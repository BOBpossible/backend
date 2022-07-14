package cmc.bobpossible.push.dto;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.push.PushNotification;
import cmc.bobpossible.push.PushType;
import lombok.Data;

import javax.persistence.*;

@Data
public class GetPushRes {
    private Long id;

    private String title;

    private String storeName;

    private String subTitle;

    private PushType pushType;

    private boolean checked;

    public GetPushRes(PushNotification pushNotification) {
        this.id = pushNotification.getId();
        this.title = pushNotification.getTitle();
        this.storeName = pushNotification.getStoreName();
        this.subTitle = pushNotification.getSubTitle();
        this.pushType = pushNotification.getPushType();
        this.checked = pushNotification.isChecked();
    }
}
