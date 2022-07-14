package cmc.bobpossible.member.controller;


import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.member.MemberService;
import cmc.bobpossible.member.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final MemberService memberService;

    @ApiOperation("고객 회원가입")
    @PostMapping("")
    public BaseResponse<String> joinUser(@Validated @RequestBody PostUserReq postUserReq, Errors errors) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        memberService.joinUser(postUserReq);
        return new BaseResponse<>("");
    }

    @ApiOperation("회원 탈퇴")
    @PatchMapping("/me/quit")
    public BaseResponse<String> deleteUser() throws BaseException {

        memberService.deleteUser();

        return new BaseResponse<>("");
    }

    @ApiOperation("내 정보 조회(마이페이지)")
    @GetMapping("/me")
    public BaseResponse<GetUser> getUser() throws BaseException {

        return new BaseResponse<>(memberService.getUser());
    }

    @ApiOperation("등록상태 조회")
    @GetMapping("/me/register-status")
    public BaseResponse<GetUserRegisterStatus> getUserRegisterStatus() throws BaseException {

        return new BaseResponse<>(memberService.getUserRegisterStatus());
    }

    @ApiOperation("내 정보 수정(마이페이지)")
    @PatchMapping("/me")
    public BaseResponse<String> patchUser(@RequestParam String email) throws BaseException {

        memberService.patchUser(email);

        return new BaseResponse<>("");
    }

    @ApiOperation("내 정보 수정(마이페이지)")
    @PatchMapping("image/me")
    public BaseResponse<String> patchUserImage(@RequestPart MultipartFile profileImage) throws BaseException, IOException {

        memberService.patchUserImage(profileImage);

        return new BaseResponse<>("");
    }

    @ApiOperation("주소 조회")
    @GetMapping("/me/address")
    public BaseResponse<GetAddressRes> getUserAddress() throws BaseException {

        return new BaseResponse<>(memberService.getUserAddress());
    }

    @ApiOperation("알림 설정 조회")
    @GetMapping("/me/notification")
    public BaseResponse<GetNotificationRes> getUserNotification() throws BaseException {

        return new BaseResponse<>(memberService.getUserNotification());
    }

    @ApiOperation("알림 설정 수정")
    @PatchMapping("/me/notification")
    public BaseResponse<String> patchUserNotification(@RequestBody PatchUserNotificationReq patchUserNotificationReq) throws BaseException {

        memberService.patchUserNotification(patchUserNotificationReq);

        return new BaseResponse<>("");
    }

    @ApiOperation("내 주소 수정")
    @PatchMapping("me/address")
    public BaseResponse<String> patchUserAddress(@Validated @RequestBody AddressDto addressDto, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        memberService.patchUserAddress(addressDto);

        return new BaseResponse<>("");
    }

}
