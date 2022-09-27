package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.entity.Member;
import lombok.Data;

@Data
public class GetUser {

    private Long userId;
    private String name;
    private String email;
    private int point;
    private boolean isAuthentication;
    private String phone;

    public GetUser(Member member) {
        this.userId = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.point = member.getTotalPoints();
        this.isAuthentication = true;
        if (member.getPhone() != null) {
            this.phone = member.getPhone().substring(7);
        }
    }
}
