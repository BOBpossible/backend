package cmc.bobpossible.review_image;

import cmc.bobpossible.review.Review;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class ReviewImage {

    @Id
    @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Column(columnDefinition = "TEXT")
    private String image;

    protected ReviewImage() {
    }

    @Builder
    public ReviewImage(Long id, Review review, String image) {
        this.id = id;
        this.review = review;
        this.image = image;
    }

    public void addReview(Review review) {
        this.review = review;
    }
}
