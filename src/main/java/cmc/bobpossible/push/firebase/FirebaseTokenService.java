package cmc.bobpossible.push.firebase;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
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

    public void test() throws BaseException, IOException {

        Optional<FirebaseToken> byKey = firebaseTokenRepository.findByKey(25L);

        FirebaseToken firebaseToken = byKey.get();


        fcmService.sendMessageTo(firebaseToken.getValue(),"success", "success", "미션에 성공하였습니다.", "");
    }
}
