package cmc.bobpossible.review.dto;

import cmc.bobpossible.review.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReviewDto {

    private String name;
    private double rate;
    private String content;
    List<ImageDto> images;
    private LocalDateTime date;

    public ReviewDto(Review review) {
        this.name = review.getMember().getName();
        this.rate = review.getRate();
        this.content = review.getContent();
        this.images = review.getReviewImages().stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
        this.date = review.getUpdateAt();
    }
}
