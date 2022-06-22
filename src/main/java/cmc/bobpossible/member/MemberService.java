package cmc.bobpossible.member;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.dto.PostOwnerReq;
import cmc.bobpossible.member.dto.PostUserReq;
import cmc.bobpossible.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinUser(PostUserReq postUserReq) throws BaseException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        member.joinUser(postUserReq.getName(),
                postUserReq.getGender(),
                postUserReq.getBirthDate(),
                postUserReq.getPhone(),
                new Address(postUserReq.getAddressStreet(),postUserReq.getAddressDong(), postUserReq.getX(), postUserReq.getY()),
                new Terms(postUserReq.getTermsOfService(),postUserReq.getPrivacyPolicy(),postUserReq.getLocationInfo(), postUserReq.getMarketing()));

    }

    @Transactional
    public void joinOwner(PostOwnerReq postOwnerReq) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        member.joinOwner(postOwnerReq.getName(),
                postOwnerReq.getGender(),
                postOwnerReq.getBirthDate(),
                postOwnerReq.getPhone(),
                new Terms(postOwnerReq.getTermsOfService(),postOwnerReq.getPrivacyPolicy(),postOwnerReq.getLocationInfo(), postOwnerReq.getMarketing()));
    }
}
