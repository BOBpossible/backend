package cmc.bobpossible.member;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
public class Notification {

    private Boolean event;
    private Boolean review;
    private Boolean question;

    protected Notification() {
    }

    @Builder
    public Notification(Boolean event, Boolean review, Boolean question) {
        this.event = event;
        this.review = review;
        this.question = question;
    }

    public void update(Boolean event, Boolean question, Boolean review) {
        this.event = event;
        this.review = review;
        this.question = question;
    }
}
