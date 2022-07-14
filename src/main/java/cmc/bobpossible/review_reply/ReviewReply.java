package cmc.bobpossible.review_reply;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.review.Review;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class ReviewReply extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Column(length = 300)
    private String reply;

    @Builder
    public ReviewReply(Long id, Review review, String reply) {
        this.id = id;
        this.review = review;
        this.reply = reply;
    }

    protected ReviewReply() {
    }

    public void addReview(Review review) {
        this.review = review;
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
    }
}
