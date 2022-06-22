package cmc.bobpossible.review;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.review.dto.GetReviewImagesRes;
import cmc.bobpossible.review.dto.GetReviewsRes;
import cmc.bobpossible.review.dto.PostReviewReq;
import cmc.bobpossible.store.Store;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation("리뷰 등록")
    @PostMapping("/me")
    public BaseResponse<String> createReview(@Validated @RequestBody PostReviewReq postReviewReq, @RequestPart List<MultipartFile> reviewImage, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        reviewService.createReview(postReviewReq, reviewImage);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 리뷰 조회")
    @GetMapping("/{storeId}")
    public BaseResponse<GetReviewsRes> getReviews(@PathVariable Long storeId) throws BaseException {

        Store store = reviewService.getReviews(storeId);

        return new BaseResponse<>(new GetReviewsRes(store));
    }

    @ApiOperation("리뷰 사진 조회")
    @GetMapping("/images/{storeId}")
    public BaseResponse<GetReviewImagesRes> getReviewImages(@PathVariable Long storeId) throws BaseException {

        Store store = reviewService.getReviews(storeId);

        return new BaseResponse<>(new GetReviewImagesRes(store));
    }
}
