package cmc.bobpossible.config.auth.dto;

import cmc.bobpossible.member.Role;
import lombok.Builder;

public class LoginRes {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
    private String registerStatus;
    private String role;

    @Builder
    public LoginRes(String grantType, String accessToken, Long accessTokenExpiresIn, String refreshToken, String registerStatus, String role) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
        this.registerStatus = registerStatus;
        this.role = role;
    }
}
