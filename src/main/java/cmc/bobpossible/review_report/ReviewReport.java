package cmc.bobpossible.review_report;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.review.Review;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class ReviewReport extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_report_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    private String content;
}
