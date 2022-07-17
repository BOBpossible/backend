package cmc.bobpossible.mission;

import cmc.bobpossible.Status;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.dto.GetHome;
import cmc.bobpossible.mission.dto.GetMissionMapRes;
import cmc.bobpossible.mission.dto.GetOwnerMissionRes;
import cmc.bobpossible.mission.dto.OwnerMissionDto;
import cmc.bobpossible.mission_group.MissionGroup;
import cmc.bobpossible.mission_group.MissionGroupRepository;
import cmc.bobpossible.point.Point;
import cmc.bobpossible.push.PushNotification;
import cmc.bobpossible.push.PushNotificationRepository;
import cmc.bobpossible.push.firebase.FCMService;
import cmc.bobpossible.push.firebase.FirebaseToken;
import cmc.bobpossible.push.firebase.FirebaseTokenRepository;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MissionGroupRepository missionGroupRepository;
    private final FCMService fcmService;
    private final FirebaseTokenRepository firebaseTokenRepository;
    private final PushNotificationRepository pushNotificationRepository;

    @Transactional
    public GetHome getMissions() throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Mission> missions = missionRepository.findByMember(member);

        // 현재 미션들의 만료일 체크
        missions.removeIf(Mission::checkValidation);

        // 현재 미션 할당된 미션 없음
        if(missions.isEmpty()){
            // 미션할당
            // 같은 동 가게 목록
            List<Store> stores = storeRepository.findByAddressDong(member.getAddress().getDong());
            //x,y
            double mem_x = member.getAddress().getX();
            double mem_y = member.getAddress().getY();
            // 거리 계산
            List<Recommend> recommends = stores.stream().map( s -> new Recommend(s, mem_x, mem_y)).collect(Collectors.toList());
            // 거리 가중치 계산
            recommends.forEach(Recommend::distancePercentage);

            //취향 가중치 계산
            recommends.forEach(r-> r.preference(member.getMemberCategories(), recommends.size()));

            // 최종 확률 계산
            recommends.forEach(Recommend::getCalculatePercentage);

            //정렬
            recommends.sort(new RecommendPercentageComparator().reversed());

            List<Recommend> res = new ArrayList<>();

            if (recommends.size() < 3) { // 현재 가게 3개 이하 일 경우
                return new GetHome(member, recommends.stream()
                        .map(
                                r -> missionRepository.save(
                                        Mission.builder()
                                                .member(member)
                                                .missionGroup(r.getMissionGroup())
                                                .build()))
                        .collect(Collectors.toList()));
            } else {
                while (res.stream().distinct().count() < 3) { //가게가 3개 이상일 때 3개 추첨
                    Recommend random = WeightedRandom.getRandom(recommends);
                    if (random != null) {
                        res.add(random);
                        random.delete();
                        recommends.remove(random);
                    }
                }
                return new GetHome(member, res.stream()
                        .distinct()
                        .filter(this::isNotNull)
                        .map(
                                r ->  missionRepository.save(
                                        Mission.builder()
                                                .member(member)
                                                .missionGroup(r.getMissionGroup())
                                                .build()))
                        .collect(Collectors.toList()));
            }
        }
        return new GetHome(member, missions);
    }

    private boolean isNotNull(Recommend r) {
        return r != null;
    }

    public List<Mission> getMissionOnProgress() throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        return missionRepository.findByMemberAndOnProgress(member, true);
    }

    public List<Mission> getCompleteMission() throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Mission> byMemberAndMissionStatus = missionRepository.findByMemberAndMissionStatusAndStatus(member.getId(), MissionStatus.DONE, Status.EXPIRED);

        log.info(String.valueOf(byMemberAndMissionStatus.size()));

        return missionRepository.findByMemberAndMissionStatus(member, MissionStatus.DONE);
    }

    @Transactional
    public void postRequestCompleteMission(Long missionId) throws BaseException {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        //사장 푸시 알림
        // 알림여부 체크
//        fcmService.sendMessageTo();

        mission.requestComplete();
    }

    public GetMissionMapRes getMissionsMap(Long missionId) throws BaseException {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        return new GetMissionMapRes(mission);

    }

    public GetOwnerMissionRes getOwnersMissionOnProgress() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<MissionGroup> groups = missionGroupRepository.findByStore(member.getStore());

        List<Mission> missions = new ArrayList<>();

        groups.forEach(g -> missions.addAll(missionRepository.findByMissionGroupAndMissionStatus(g, MissionStatus.PROGRESS)));

        return new GetOwnerMissionRes(missions);
    }

    public List<OwnerMissionDto> getOwnersMissionOnSuccess() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<MissionGroup> groups = missionGroupRepository.findByStore(member.getStore());

        List<Mission> missions = new ArrayList<>();

        groups.forEach(g -> missions.addAll(missionRepository.findByMissionGroupAndMissionStatus(g, MissionStatus.CHECKING)));

        return missions.stream()
                .map(OwnerMissionDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void missionChallenge(Long missionId) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Mission> valid = missionRepository.findByMemberAndOnProgress(member, true);

        if (valid.size() > 1) {
            throw  new BaseException(MISSION_ON_PROGRESS_EXIST);
        }

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        mission.challengeMission();
    }

    @Transactional
    public void cancelMission(Long missionId) throws BaseException {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        mission.cancelMission();
    }

    @Transactional
    public void missionSuccess(Long missionId) throws BaseException, IOException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        mission.successMission();

        //포인트적립
        member.addPoint(Point.builder()
                .point(mission.getMissionGroup().getPoint())
                .title(mission.getMissionGroup().getStore().getName())
                .subtitle(mission.getMissionGroup().getMissionContent())
                .build());

        //리워드 증가
        member.getReward().addCounter();

        // 리뷰 작성 (푸시 알림) 알림여부 체크
        if (member.getNotification().getReview()) {
            FirebaseToken firebaseToken = firebaseTokenRepository.findByKey(member.getId())
                    .orElseThrow(() -> new BaseException(CHECK_FCM_TOKEN));
            fcmService.sendReviewPush(firebaseToken.getValue(), member,  mission.getMissionGroup().getStore(), mission);
        }
    }

    @Transactional
    public void acceptMission(Long missionId) throws BaseException, IOException {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        FirebaseToken firebaseToken = firebaseTokenRepository.findByKey(mission.getMember().getId())
                .orElseThrow(() -> new BaseException(CHECK_FIREBASE_TOKEN));

        fcmService.sendMessageTo(firebaseToken.getValue(),mission.getMissionGroup().getStore().getName(), "사장님이 성공요청을 수락하셨습니다. 확인 버튼을 눌러 미션을 완료해주세요!", "missionSuccess", "");
        pushNotificationRepository.save(PushNotification.createMissionSuccessPush(mission.getMember(), mission.getMissionGroup().getStore(), mission));

        mission.acceptMission();
    }

    @Transactional
    public void denyMission(Long missionId) throws BaseException, IOException {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        FirebaseToken firebaseToken = firebaseTokenRepository.findByKey(mission.getMember().getId())
                .orElseThrow(() -> new BaseException(CHECK_FIREBASE_TOKEN));

        fcmService.sendMessageTo(firebaseToken.getValue(),mission.getMissionGroup().getStore().getName(), "사장님이 성공요청을 거절하셨습니다. 다시 한번 확인해주세요!", "missionDenied", "");
        pushNotificationRepository.save(PushNotification.createMissionDeniedPush(mission.getMember(), mission.getMissionGroup().getStore(), mission));

        mission.deniedMission();
    }
}
