package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.RegisterStatus;
import cmc.bobpossible.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class GetUserRegisterStatus {

    private RegisterStatus registerStatus;

    @Builder
    public GetUserRegisterStatus(Member member) {
        this.registerStatus = member.getRegisterStatus();
    }
}
