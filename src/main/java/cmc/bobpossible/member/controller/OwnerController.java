package cmc.bobpossible.member.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.member.MemberService;
import cmc.bobpossible.member.dto.PostOwnerReq;
import cmc.bobpossible.member.dto.PostUserReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final MemberService memberService;

    @ApiOperation("사장 회원가입")
    @PostMapping("/")
    public BaseResponse<String> joinOwner(@Validated @RequestBody PostOwnerReq postOwnerReq, Errors errors) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        memberService.joinOwner(postOwnerReq);
        return new BaseResponse<>("");
    }
}
