package cmc.bobpossible.review_reply;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.review.Review;
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
}
