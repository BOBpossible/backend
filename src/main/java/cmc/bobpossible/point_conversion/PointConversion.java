package cmc.bobpossible.point_conversion;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class PointConversion  extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_conversion_id")
    private Long id;

    private int point;

    private String name;

    private String bank;

    private String accountNumber;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    protected PointConversion() {
    }

    @Builder
    public PointConversion(Long id, int point, String name, String bank, String accountNumber, Member member) {
        this.id = id;
        this.point = point;
        this.name = name;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.member = member;
    }

}
