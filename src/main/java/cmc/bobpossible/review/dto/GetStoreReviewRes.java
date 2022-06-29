package cmc.bobpossible.review.dto;

import cmc.bobpossible.review.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetStoreReviewRes {

    private Long reviewId;
    private String name;
    private double rate;
    private String content;
    private LocalDateTime date;
    private List<ImageDto> images;
    private List<ReviewReplyDto> reply;

    public GetStoreReviewRes(Review review) {
        this.reviewId = review.getId();
        this.name = review.getMember().getName();
        this.rate = review.getRate();
        this.content = review.getContent();
        this.date = review.getUpdateAt();
        this.images = review.getReviewImages().stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
        this.reply = review.getReviewReplies().stream()
                .map(ReviewReplyDto::new)
                .collect(Collectors.toList());
    }
}
