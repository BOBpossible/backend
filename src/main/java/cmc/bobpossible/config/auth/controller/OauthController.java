package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.dto.AppleLoginReq;
import cmc.bobpossible.config.auth.dto.PhoneValidationDto;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class OauthController {

    private final OauthService oauthService;

    @Value("${jwt.secret}")
    private String value;

    @ApiOperation(value = "로그인 성공")
    @GetMapping("/success")
    public String jwtResponse(@RequestParam("grantType") String grantType, @RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken, @RequestParam("accessTokenExpiresIn") Long accessTokenExpiresIn, @RequestParam("registerStatus") String registerStatus) {


        return "";
    }

    @ApiOperation(value = "토큰 갱신")
    @PostMapping("/token")
    public BaseResponse<TokenDto> reissueToken(@RequestParam String accessToken, @RequestParam String refreshToken) throws BaseException {

        return new BaseResponse<>(oauthService.reissueToken(accessToken, refreshToken));
    }

    @GetMapping("/health")
    public String checkHealth() {
        return "healthy";
    }

    @PostMapping("/authorization/login")
    public BaseResponse<TokenDto> login(@RequestParam String email, @RequestParam String name) {
        return new BaseResponse<>(oauthService.login(email, name));
    }

    @PostMapping("/authorization/apple-login")
    public BaseResponse<TokenDto> appleLogin(@RequestBody AppleLoginReq appleLoginReq) {
        return new BaseResponse<>(oauthService.appleLogin(appleLoginReq));
    }

    @PostMapping("/phone-validation")
    public BaseResponse<PhoneValidationDto> phoneValidation(@RequestParam String phone) {
        return new BaseResponse<>(oauthService.phoneValidation(phone));
    }

}
