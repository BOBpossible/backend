package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.entity.Member;
import lombok.Data;

@Data
public class GetNotificationRes {

    private Boolean event;
    private Boolean review;
    private Boolean question;

    public GetNotificationRes(Member member) {
        this.event = member.getNotification().getEvent();
        this.review = member.getNotification().getReview();
        this.question = member.getNotification().getQuestion();
    }

}
