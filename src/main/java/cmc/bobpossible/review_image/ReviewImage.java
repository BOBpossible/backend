package cmc.bobpossible.review_image;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.review.Review;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class ReviewImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @Column(columnDefinition = "TEXT")
    private String image;

    protected ReviewImage() {
    }

    @Builder
    public ReviewImage(Long id, Review review, String image, Store store) {
        this.id = id;
        this.review = review;
        this.image = image;
        this.store = store;
    }

    public void addReview(Review review) {
        this.review = review;
    }
}
