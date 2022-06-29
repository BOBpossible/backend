package cmc.bobpossible.review.dto;

import cmc.bobpossible.review_reply.ReviewReply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewReplyDto {

    private Long reviewReplyId;
    private String reply;
    private LocalDateTime date;

    public ReviewReplyDto(ReviewReply reviewReply) {
        this.reviewReplyId = reviewReply.getId();
        this.reply = reviewReply.getReply();
        this.date =reviewReply.getUpdateAt();
    }
}
