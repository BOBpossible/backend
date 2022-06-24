package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionService;
import cmc.bobpossible.mission.dto.GetMissionsRes;
import cmc.bobpossible.review.ReviewService;
import cmc.bobpossible.review.dto.GetReviewImagesRes;
import cmc.bobpossible.review.dto.PostReviewReq;
import cmc.bobpossible.store.Store;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
