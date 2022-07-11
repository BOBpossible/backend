package cmc.bobpossible.review;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionRepository;
import cmc.bobpossible.review.dto.PostReviewReq;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.review_image.ReviewImageRepository;
import cmc.bobpossible.review_reply.ReviewReply;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import cmc.bobpossible.review.dto.GetStoreReviewRes;
import cmc.bobpossible.store.dto.GetStoreImages;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public Review createReview(PostReviewReq postReviewReq) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Store store = storeRepository.findById(postReviewReq.getStoreId())
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        Mission mission = missionRepository.findById(postReviewReq.getMissionId())
                .orElseThrow(() -> new BaseException(INVALID_MISSION_ID));

        mission.reviewDone();

        Review review = Review.builder()
                .content(postReviewReq.getContent())
                .member(member)
                .rate(postReviewReq.getRate())
                .store(store)
                .build();

        reviewRepository.save(review);

        return  review;
    }

    @Transactional
    public void createReviewImage(List<MultipartFile> reviewImage, Long reviewId) throws IOException, BaseException {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(INVALID_REVIEW_ID));

        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < reviewImage.size() && i < 3; i++) {
            imageURL.add( s3Uploader.upload(reviewImage.get(i), "menuImage"));
        }

        // 메뉴 이미지 엔티티
        List<ReviewImage> reviewImages = imageURL.stream()
                .map(i -> ReviewImage.builder().image(i).review(review).store(review.getStore()).build())
                .collect(Collectors.toList());

        review.addReviewImages(reviewImages);

    }

    public Slice<GetStoreReviewRes> getStoreReviewRes(Long storeId, Pageable pageable) throws BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        Slice<Review> reviews = reviewRepository.findByStoreOrderByIdDesc(store, pageable);

        return reviews.map(GetStoreReviewRes::new);
    }

    public Slice<GetStoreReviewRes> getMyReviews(Pageable pageable) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Slice<Review> reviews = reviewRepository.findByMemberOrderByIdDesc(member, pageable);

        return reviews.map(GetStoreReviewRes::new);
    }

    @Transactional
    public void deleteReview(Long reviewId) throws BaseException {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(INVALID_REVIEW_ID));

        review.delete();

    }

    public Slice<GetStoreImages> getStoreImages(Long storeId, Pageable pageable) throws BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));


        Slice<ReviewImage> images = reviewImageRepository.findByStoreOrderByIdDesc(store, pageable);

        return images.map(GetStoreImages::new);
    }

    @Transactional
    public void postReviewReply(Long reviewId, PostReviewReq postReviewReq) throws BaseException {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(INVALID_REVIEW_ID));

        ReviewReply reviewReply = ReviewReply.builder()
                .reply(postReviewReq.getContent())
                .review(review)
                .build();

        review.addReviewReply(reviewReply);
    }
}
