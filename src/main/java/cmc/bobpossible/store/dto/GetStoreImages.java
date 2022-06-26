package cmc.bobpossible.store.dto;

import cmc.bobpossible.review_image.ReviewImage;
import lombok.Builder;
import lombok.Data;

@Data
public class GetStoreImages {

    private Long reviewId;
    private String imageUrl;

    public GetStoreImages(ReviewImage reviewImage) {
        this.reviewId = reviewImage.getReview().getId();
        this.imageUrl = reviewImage.getImage();
    }
}
