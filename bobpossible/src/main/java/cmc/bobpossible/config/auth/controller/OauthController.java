package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
public class OauthController {

    @ApiOperation(value = "로그인 성공")
    @GetMapping("/auth/success")
    public BaseResponse<TokenDto> jwtResponse(@RequestParam String grantType, @RequestParam String accessToken, @RequestParam String refreshToken, @RequestParam Long accessTokenExpiresIn) {

        return new BaseResponse<>(new TokenDto(grantType,accessToken,accessTokenExpiresIn,refreshToken));
    }
}
