package cmc.bobpossible.member;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
public class Notification {

    private Boolean mission;
    private Boolean event;
    private Boolean review;
    private Boolean question;

    protected Notification() {
    }

    @Builder
    public Notification(Boolean mission, Boolean event, Boolean review, Boolean question) {
        this.mission = mission;
        this.event = event;
        this.review = review;
        this.question = question;
    }

    public void updateUser(Boolean event, Boolean question, Boolean review) {
        this.event = event;
        this.review = review;
        this.question = question;
    }

    public void updateOwner(Boolean mission, Boolean event, Boolean question, Boolean review) {
        this.mission = mission;
        this.event = event;
        this.review = review;
        this.question = question;
    }
}
