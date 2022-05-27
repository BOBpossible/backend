package cmc.bobpossible.point;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.Member;
import cmc.bobpossible.store.Store;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Point extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private int point;
}
