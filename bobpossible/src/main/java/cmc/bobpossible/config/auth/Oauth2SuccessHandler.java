package cmc.bobpossible.config.auth;

import cmc.bobpossible.refreshToken.RefreshToken;
import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import cmc.bobpossible.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class Oauth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TokenDto token = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(token.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        String targetUrl = UriComponentsBuilder.fromUriString("/auth/success")
                .queryParam("grantType", token.getGrantType())
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("cmc/bobpossible/refreshToken", token.getRefreshToken())
                .queryParam("accessTokenExpiresIn", token.getAccessTokenExpiresIn())
                .build().toUriString();
//        if (response.isCommitted()) {
//            return;
//        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
