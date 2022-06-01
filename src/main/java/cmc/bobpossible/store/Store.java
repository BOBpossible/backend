package cmc.bobpossible.store;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.Member;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Store extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(length = 100)
    private String name;

    @Column(length = 50)
    private String intro;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String bannerImage;

    @Column(length = 100)
    private String representativeMenu;

    @Column(columnDefinition = "TEXT")
    private String representativeMenuImage;

    private int tableCount;

}
