package cmc.bobpossible.review;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.review.dto.PostReviewReq;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;
import static cmc.bobpossible.config.BaseResponseStatus.INVALID_STORE_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createReview(PostReviewReq postReviewReq, List<MultipartFile> reviewImage) throws IOException, BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Store store = storeRepository.findById(postReviewReq.getStoreId())
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        // 최대 3번
        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < reviewImage.size() || i < 3; i++) {
            imageURL.add( s3Uploader.upload(reviewImage.get(i), "menuImage"));
        }

        // 메뉴 이미지 엔티티
        List<ReviewImage> reviewImages = imageURL.stream()
                .map(i -> ReviewImage.builder().image(i).build())
                .collect(Collectors.toList());



        reviewRepository.save(
                Review.builder()
                        .content(postReviewReq.getContent())
                        .member(member)
                        .rate(postReviewReq.getRate())
                        .store(store)
                        .reviewImages(reviewImages)
                        .build());


    }

    public Store getReviews(Long storeId) throws BaseException {

        return storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));


    }
}
