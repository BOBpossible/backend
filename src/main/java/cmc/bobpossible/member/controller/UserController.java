package cmc.bobpossible.member.controller;


import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.member.MemberService;
import cmc.bobpossible.member.dto.GetUser;
import cmc.bobpossible.member.dto.PostUserReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("내 정보 조회(마이페이지)")
    @PostMapping("/me")
    public BaseResponse<GetUser> getUser() throws BaseException {

        return new BaseResponse<>(memberService.getUser());
    }
}
