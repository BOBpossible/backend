package cmc.bobpossible.review.dto;

import cmc.bobpossible.review.Review;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.store.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetReviewsRes {

    private int reviewCount;
    private List<ReviewDto> reviewDto;

    public GetReviewsRes(Store store) {
        this.reviewCount = store.getReviewCount();
        this.reviewDto = store.getReviews().stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }
}
