package cmc.bobpossible.config.auth.dto;

import cmc.bobpossible.member.Member;
import cmc.bobpossible.member.RegisterStatus;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private RegisterStatus registerStatus;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.registerStatus = member.getRegisterStatus();
    }
}
