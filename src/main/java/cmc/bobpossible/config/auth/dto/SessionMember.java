package cmc.bobpossible.config.auth.dto;

import cmc.bobpossible.member.Role;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.member.RegisterStatus;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private RegisterStatus registerStatus;
    private Role role;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.registerStatus = member.getRegisterStatus();
        this.role = member.getRole();
    }
}
