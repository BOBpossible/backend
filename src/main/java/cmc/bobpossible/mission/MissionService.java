package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;
import static cmc.bobpossible.config.BaseResponseStatus.INVALID_MISSION_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;

    public List<Mission> getMissions() throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        return missionRepository.findByMember(member);
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
