package cmc.bobpossible.review;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private double rate;

    @Column(length = 300)
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    protected Review() {
    }

    @Builder
    public Review(Long id, Member member, Store store, double rate, String content, List<ReviewImage> reviewImages) {
        this.id = id;
        this.member = member;
        this.store = store;
        this.rate = rate;
        this.content = content;

        reviewImages.forEach(this::addReviewImage);
    }

    private void addReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        reviewImage.addReview(this);
    }

    public void addReviewImages(List<ReviewImage> reviewImages) {
        reviewImages.forEach(this::addReviewImage);
    }
}
