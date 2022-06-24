package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import cmc.bobpossible.utils.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;
import static cmc.bobpossible.config.BaseResponseStatus.INVALID_MISSION_ID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public List<Mission> getMissions() throws BaseException {
        Member member = memberRepository.findById(13L)
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

            recommends.forEach(Recommend::distancePercentage);

            recommends.forEach(r-> r.preference(member.getMemberCategories(), recommends.size()));

            recommends.forEach(Recommend::calculatePercentage);

            recommends.sort(new RecommendPercentageComparator().reversed());

            List<Store> res = new ArrayList<>();


            if (recommends.size() < 3) {
                return recommends.stream()
                        .map(
                                r -> missionRepository.save(
                                        Mission.builder()
                                                .member(member)
                                                .store(r.getStore())
                                                .build()))
                        .collect(Collectors.toList());
            } else {
                while (res.stream().distinct().count() <= 3) {
                    res.add(WeightedRandom.getRandom(recommends));
                    System.out.println(res);
                }
                return res.stream()
                        .distinct()
                        .filter(this::isNotNull)
                        .map(
                                r ->  missionRepository.save(
                                        Mission.builder()
                                                .member(member)
                                                .store(r)
                                                .build()))
                        .collect(Collectors.toList());
            }
        }

        return missions;
    }

    private boolean isNotNull(Store r) {
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
}
