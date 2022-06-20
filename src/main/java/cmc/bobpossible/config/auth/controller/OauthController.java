package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.refreshToken.RefreshToken;
import cmc.bobpossible.utils.S3Uploader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @PostMapping("/authorization/google")
    public BaseResponse<TokenDto> googleLogin(@RequestParam String email, @RequestParam String name) {
        return new BaseResponse<>(oauthService.googleLogin(email, name));
    }



}
