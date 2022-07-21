package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.entity.Member;
import lombok.Data;

@Data
public class GetNotificationOwnerRes {

    private Boolean mission;
    private Boolean event;
    private Boolean review;
    private Boolean question;

    public GetNotificationOwnerRes(Member member) {
        this.mission = member.getNotification().getMission();
        this.event = member.getNotification().getEvent();
        this.review = member.getNotification().getReview();
        this.question = member.getNotification().getQuestion();
    }
}
