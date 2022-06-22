package cmc.bobpossible.review.dto;

import cmc.bobpossible.review_image.ReviewImage;
import lombok.Data;

@Data
public class ImageDto {
    private String imageUrl;

    public ImageDto(ReviewImage image) {
        this.imageUrl = image.getImage();
    }
}
