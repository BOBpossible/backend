package cmc.bobpossible.config.auth.controller;

import cmc.bobpossible.config.BaseException;
<<<<<<< HEAD
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.refreshToken.RefreshToken;
import cmc.bobpossible.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

=======
import cmc.bobpossible.config.BaseResponseStatus;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import cmc.bobpossible.refreshToken.RefreshToken;
import cmc.bobpossible.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> 1512c441780c7c79413647d4d638da0dd153e401
import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OauthService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
<<<<<<< HEAD
    private final MemberRepository memberRepository;
=======
>>>>>>> 1512c441780c7c79413647d4d638da0dd153e401

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
<<<<<<< HEAD

    @Transactional
    public TokenDto googleLogin(String email, String name) {

        Member member = memberRepository.findByEmail(email)
                .orElse(Member.create(email, name));

        Authentication auth = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        TokenDto token = tokenProvider.generateTokenDto(auth);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(auth.getName())
                .value(token.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        memberRepository.save(member);

        return TokenDto.builder()
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiresIn(token.getAccessTokenExpiresIn())
                .registerStatus(member.getRegisterStatus().name())
                .build();
    }
=======
>>>>>>> 1512c441780c7c79413647d4d638da0dd153e401
}
