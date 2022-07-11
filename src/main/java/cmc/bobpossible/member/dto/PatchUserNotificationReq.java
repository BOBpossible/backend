package cmc.bobpossible.member.dto;

import lombok.Data;

@Data
public class PatchUserNotificationReq {

    private Boolean event;
    private Boolean review;
    private Boolean question;
}
