package cmc.bobpossible.config.auth.dto;

import cmc.bobpossible.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String image;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();

    }
}
