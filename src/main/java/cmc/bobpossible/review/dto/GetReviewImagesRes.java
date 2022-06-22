package cmc.bobpossible.review.dto;

import cmc.bobpossible.review.Review;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.store.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetReviewImagesRes {

    List<ImageDto> images;

    public GetReviewImagesRes(Store store) {
        List< ReviewImage > reviewImages = new ArrayList<>();
        List<List<ReviewImage>> collect = store.getReviews().stream()
                .map(Review::getReviewImages)
                .collect(Collectors.toList());
        collect.forEach(reviewImages::addAll);

        this.images = reviewImages.stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
    }
}
