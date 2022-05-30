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

//    private final JwtService jwtService;
//    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        HttpSession session = request.getSession();
//        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
//        Optional<Member> member = memberRepository.findByEmail(sessionMember.getEmail());
//
//        String jwt = jwtService.createJwt(member.get().getId());
//        System.out.println(member.get().getId()+"??????????????????????????????????????????????");
//        System.out.println(sessionMember.getId()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(jwt);
//
//        String targetUrl = UriComponentsBuilder.fromUriString("/auth")
//                .queryParam("jwt",jwt)
//                .queryParam("id", member.get().getId())
//                .build().toUriString();
//        getRedirectStrategy().sendRedirect(request,response,targetUrl);
        TokenDto token = tokenProvider.generateTokenDto(authentication);
        String targetUrl = UriComponentsBuilder.fromUriString("/auth")
                .queryParam("token", token.getAccessToken())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
