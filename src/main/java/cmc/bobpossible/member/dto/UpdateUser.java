package cmc.bobpossible.member.dto;

import lombok.Data;

@Data
public class UpdateUser {

    private String name;
    private String email;

    public UpdateUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
