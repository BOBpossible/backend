package cmc.bobpossible.push.dto;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.push.PushNotification;
import cmc.bobpossible.push.PushType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class GetPushRes {
    private Long id;

    private String name;

    private String title;

    private String subTitle;

    private PushType pushType;

    private boolean checked;

    private Long subId;

    private LocalDateTime date;

    public GetPushRes(PushNotification pushNotification) {
        this.id = pushNotification.getId();
        this.date = pushNotification.getCreateAt();
        this.name = pushNotification.getName();
        this.title = pushNotification.getTitle();
        this.subTitle = pushNotification.getSubTitle();
        this.pushType = pushNotification.getPushType();
        this.checked = pushNotification.isChecked();
        this.subId = pushNotification.getSubId();
    }
}
