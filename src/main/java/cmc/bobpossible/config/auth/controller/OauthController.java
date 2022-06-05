package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public BaseResponse<TokenDto> jwtResponse(@RequestParam("grantType") String grantType, @RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken, @RequestParam("accessTokenExpiresIn") Long accessTokenExpiresIn) {

        return new BaseResponse<>(new TokenDto(grantType, accessToken, accessTokenExpiresIn, refreshToken));
    }

    @ApiOperation(value = "토큰 갱신")
    @PostMapping("/auth/token")
    public BaseResponse<TokenDto> reissueToken(@RequestParam String accessToken, @RequestParam String refreshToken) {

        return new BaseResponse<>(oauthService.reissueToken(accessToken, refreshToken));
    }

    @GetMapping("/health")
    public String checkHealth() {
        return "healthy";
    }
}
