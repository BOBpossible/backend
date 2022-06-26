package cmc.bobpossible.review;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.review.dto.PostReviewReq;
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
    public BaseResponse<Long> createReview(@Validated @RequestBody PostReviewReq postReviewReq, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        return new BaseResponse<>(reviewService.createReview(postReviewReq).getId());
    }

    @ApiOperation("리뷰 이미지 등록")
    @PostMapping("/me/images/{reviewId}")
    public BaseResponse<String> createReviewImage(@RequestPart List<MultipartFile> reviewImage, @PathVariable Long reviewId) throws BaseException, IOException {

        reviewService.createReviewImage(reviewImage, reviewId);

        return new BaseResponse<>("");
    }

}
