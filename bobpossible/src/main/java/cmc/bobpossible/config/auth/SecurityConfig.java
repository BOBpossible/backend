package cmc.bobpossible.config.auth;

import cmc.bobpossible.config.auth.jwt.JwtAccessDeniedHandler;
import cmc.bobpossible.config.auth.jwt.JwtAuthenticationEntryPoint;
import cmc.bobpossible.config.auth.jwt.JwtFilter;
import cmc.bobpossible.config.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;
    private final Oauth2SuccessHandler successHandler;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtFilter(tokenProvider), OAuth2AuthorizationRequestRedirectFilter.class);

        http
                // CSRF 설정 Disable
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                // h2
                .and()
                .headers()
                .frameOptions()
                .disable()

                // 세션 설정 Stateless
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //로그인 permit all
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()

                // oauth2login
                .and()
                    .oauth2Login()
                        .successHandler(successHandler)
                        .userInfoEndpoint()
                            .userService(customOauth2UserService);
    }
}
