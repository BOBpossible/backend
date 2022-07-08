package cmc.bobpossible.review.dto;

import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.store_image.StoreImage;
import lombok.Data;

@Data
public class ImageDto {
    private String imageUrl;

    public ImageDto(ReviewImage image) {
        this.imageUrl = image.getImage();
    }

    public ImageDto(StoreImage image) {
        this.imageUrl = image.getImage();
    }

    public ImageDto(MenuImage image) {
        this.imageUrl = image.getImage();
    }
}
