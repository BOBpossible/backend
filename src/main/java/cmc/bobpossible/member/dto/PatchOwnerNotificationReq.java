package cmc.bobpossible.member.dto;

import lombok.Data;

@Data
public class PatchOwnerNotificationReq {

    private Boolean mission;
    private Boolean event;
    private Boolean review;
    private Boolean question;
}
