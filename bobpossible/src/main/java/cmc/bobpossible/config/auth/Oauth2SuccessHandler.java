package cmc.bobpossible.config.auth;

import cmc.bobpossible.config.auth.jwt.TokenDto;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class Oauth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TokenDto token = tokenProvider.generateTokenDto(authentication);
        String targetUrl = UriComponentsBuilder.fromUriString("/auth/success")
                .queryParam("grantType", token.getGrantType())
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .queryParam("accessTokenExpiresIn", token.getAccessTokenExpiresIn())
                .build().toUriString();
//        if (response.isCommitted()) {
//            return;
//        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
