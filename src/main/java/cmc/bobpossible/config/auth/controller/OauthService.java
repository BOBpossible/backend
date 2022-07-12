package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.dto.PhoneValidationDto;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.review.refreshToken.RefreshToken;
import cmc.bobpossible.review.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OauthService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Value("${sms.api}")
    String api_key;
    @Value("${sms.secret}")
    String api_secret;


    @Transactional
    public TokenDto reissueToken(String accessToken, String refreshToken) throws BaseException {
        // Refresh Token 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BaseException(INVALID_REFRESH_TOKEN);
        }

        //Access Token에서 Member Id 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        //저장소에서 memberID 기반으로 refresh token 값 가져오기
        RefreshToken refreshTokenMem = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new BaseException(MEMBER_LOGOUT));

        if (!refreshTokenMem.getValue().equals(refreshToken)) {
            throw new BaseException(CHECK_REFRESH_TOKEN);
        }

        // 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 저장소 정보 업데이트
        refreshTokenMem.updateValue(tokenDto.getRefreshToken());

        return tokenDto;
    }

    @Transactional
    public TokenDto login(String email, String name) {

        Member member = memberRepository.findByEmail(email)
                .orElse(Member.create(email, name));

        memberRepository.save(member);

        Authentication auth = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        TokenDto token = tokenProvider.generateTokenDto(auth);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(auth.getName())
                .value(token.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return TokenDto.builder()
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiresIn(token.getAccessTokenExpiresIn())
                .registerStatus(member.getRegisterStatus().name())
                .build();
    }

    public PhoneValidationDto phoneValidation(String phone) {
        Random rand  = new Random();
        String certNum = "";
        for(int i=0; i<6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            certNum+=ran;
        }

//        Message coolsms = new Message(api_key, api_secret);
//
//        // 4 params(to, from, type, text) are mandatory. must be filled
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", phone);    // 수신전화번호
//        params.put("from", "010-9805-8736");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
//        params.put("type", "SMS");
//        params.put("text", "BOB PLACE 인증번호 " + "["+certNum+"]" + "을 입력해주세요.");
//        params.put("app_version", "test app 1.2"); // application name and version
//
//        try {
//            JSONObject obj = (JSONObject) coolsms.send(params);
//            System.out.println(obj.toString());
//        } catch (CoolsmsException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }


        return new PhoneValidationDto(certNum);
    }
}
