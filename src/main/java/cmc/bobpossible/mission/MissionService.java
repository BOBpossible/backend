package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.dto.GetHome;
import cmc.bobpossible.mission.dto.GetMissionMapRes;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
            List<Recommend> recommends = stores.stream().map((Store s) -> new Recommend(s, mem_x, mem_y)).collect(Collectors.toList());

            // 거리 가중치 계산
            recommends.forEach(Recommend::distancePercentage);

            //취향 가중치 계산
            recommends.forEach(r-> r.preference(member.getMemberCategories(), recommends.size()));

            // 최종 확률 계산
            recommends.forEach(Recommend::calculatePercentage);

            //정렬
            recommends.sort(new RecommendPercentageComparator().reversed());

            List<Recommend> res = new ArrayList<>();

            // 배포 가능 가게 0개일 경우
            if (recommends.size() == 0) {
                throw new BaseException(NO_AVAILABLE_MISSION);
            }

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
                while (res.stream().distinct().count() <= 3) { //가게가 3개 이상일 때 3개 추첨
                    res.add(WeightedRandom.getRandom(recommends));
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

        return missionRepository.findByMemberAndMissionStatus(member, MissionStatus.PROGRESS);
    }

    public List<Mission> getCompleteMission() throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        return missionRepository.findByMemberAndMissionStatus(member, MissionStatus.DONE);
    }

    @Transactional
    public void postRequestCompleteMission(Long missionId) throws BaseException {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));


        mission.requestComplete();
    }

    public List<GetMissionMapRes> getMissionsMap() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Mission> missions = missionRepository.findByMember(member);

        return missions.stream()
                .map(GetMissionMapRes::new)
                .collect(Collectors.toList());

    }
}
