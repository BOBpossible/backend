package cmc.bobpossible.push;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FirebaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void postFCMToken(String token) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        FirebaseToken firebaseToken = firebaseTokenRepository.findByKey(member.getId()).orElse(FirebaseToken.builder()
                .key(member.getId())
                .build());

        firebaseToken.update(token);

        firebaseTokenRepository.save(firebaseToken);
    }
}
