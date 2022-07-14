package cmc.bobpossible.push.firebase;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FirebaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final MemberRepository memberRepository;
    private final FCMService fcmService;
    private final MissionRepository missionRepository;

    @Transactional
    public void postFCMToken(FcmToken token) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        FirebaseToken firebaseToken = firebaseTokenRepository.findByKey(member.getId()).orElse(FirebaseToken.builder()
                .key(member.getId())
                .build());

        firebaseToken.update(token.getToken());

        firebaseTokenRepository.save(firebaseToken);
    }

    public void test(long userId, long missionId) throws BaseException, IOException {

        Optional<FirebaseToken> byKey = firebaseTokenRepository.findByKey(userId);

        FirebaseToken firebaseToken = byKey.get();

        Optional<Mission> mission = missionRepository.findById(missionId);

        mission.get().acceptMission();


        fcmService.sendMessageTo(firebaseToken.getValue(),"missionSuccess", "missionSuccess", "미션에 성공하였습니다.", "");
    }

    public void test2(long userId, long missionId) throws IOException {
        Optional<FirebaseToken> byKey = firebaseTokenRepository.findByKey(userId);

        FirebaseToken firebaseToken = byKey.get();

        Optional<Mission> mission = missionRepository.findById(missionId);

        mission.get().deniedMission();


        fcmService.sendMessageTo(firebaseToken.getValue(),"missionDenied", "missionSuccess", "미션에 실패하였습니다.", "");
    }
}
