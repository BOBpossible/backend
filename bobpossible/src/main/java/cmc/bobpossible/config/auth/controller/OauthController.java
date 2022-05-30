package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class OauthController {

    @ApiOperation(value = "로그인 완료후 리다이렉트")
    @GetMapping("/auth")
    public BaseResponse<String> jwtResponse(@RequestParam String token) {
        return new BaseResponse<>(token);
    }
}
