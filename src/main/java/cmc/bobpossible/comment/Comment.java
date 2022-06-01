package cmc.bobpossible.comment;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.review.Review;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Column(columnDefinition = "TEXT")
    private String content;
}
