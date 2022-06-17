package cmc.bobpossible.payment;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Payment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    //신용카드, 체크카드
    @Column(length = 10)
    private String cardType;

    //카드 회사
    @Column(length = 20)
    private String company;

    //카드번호
    @Column(length = 20)
    private String number;

    //할부
    private int installmentPlanMonths;

    //카드사 승인번호
    @Column(length = 30)
    private String approveNo;

    //결제 금액
    private int totalAmount;

}
