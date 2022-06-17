package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class OauthController {

    private final OauthService oauthService;

    @ApiOperation(value = "로그인 성공")
    @GetMapping("/auth/success")
    public BaseResponse<String> jwtResponse(@RequestParam("grantType") String grantType, @RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken, @RequestParam("accessTokenExpiresIn") Long accessTokenExpiresIn, @RequestParam("registerStatus") String registerStatus) {


        return new BaseResponse<>("");
    }

    @ApiOperation(value = "토큰 갱신")
    @PostMapping("/auth/token")
    public BaseResponse<TokenDto> reissueToken(@RequestParam String accessToken, @RequestParam String refreshToken) throws BaseException {

        return new BaseResponse<>(oauthService.reissueToken(accessToken, refreshToken));
    }

    @GetMapping("/auth/health")
    public String checkHealth() {
        return "healthy";
    }

    @GetMapping("/me")
    public Long checkToken() {
        return SecurityUtil.getCurrentMemberId();
    }
}
