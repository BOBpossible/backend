package cmc.bobpossible.member;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.dto.GetUser;
import cmc.bobpossible.member.dto.GetUserRegisterStatus;
import cmc.bobpossible.member.dto.PostOwnerReq;
import cmc.bobpossible.member.dto.PostUserReq;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

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

    public GetUser getUser() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        return new GetUser(member);
    }

    @Transactional
    public void patchUser(String email) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        member.changeEmail(email);
    }

    @Transactional
    public void patchUserImage(MultipartFile profileImage) throws IOException, BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        String image = s3Uploader.upload(profileImage, "profileImage");

        member.changeImage(image);
    }

    public GetUserRegisterStatus getUserRegisterStatus() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        return new GetUserRegisterStatus(member);
    }
}
