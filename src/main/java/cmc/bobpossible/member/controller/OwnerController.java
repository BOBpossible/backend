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

import java.io.IOException;

@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final MemberService memberService;

    @ApiOperation("사장 회원가입")
    @PostMapping("")
    public BaseResponse<String> joinOwner(@Validated @RequestBody PostOwnerReq postOwnerReq, Errors errors) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        memberService.joinOwner(postOwnerReq);
        return new BaseResponse<>("");
    }

    @ApiOperation("알림 설정 조회")
    @GetMapping("/me/notification")
    public BaseResponse<GetNotificationOwnerRes> getOwnerNotification() throws BaseException {

        return new BaseResponse<>(memberService.getOwnerNotification());
    }

    @ApiOperation("알림 설정 수정")
    @PatchMapping("/me/notification")
    public BaseResponse<String> patchOwnerNotification(@RequestBody PatchOwnerNotificationReq patchUserNotificationReq) throws BaseException {

        memberService.patchOwnerNotification(patchUserNotificationReq);

        return new BaseResponse<>("");
    }

    @ApiOperation("사장 탈퇴")
    @PatchMapping("/me/quit")
    public BaseResponse<String> deleteOwner() throws BaseException, IOException {

        memberService.deleteOwner();

        return new BaseResponse<>("");
    }
}
