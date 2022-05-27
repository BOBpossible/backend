package cmc.bobpossible.review;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.Member;
import cmc.bobpossible.store.Store;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

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

    private int rate;

    @Column(length = 300)
    private String content;
}
