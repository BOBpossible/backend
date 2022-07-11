package cmc.bobpossible.point;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Builder;
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

    private String title;

    private String subtitle;

    private int point;

    protected Point() {
    }

    @Builder
    public Point(Long id, Member member, String title, String subtitle, int point) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.subtitle = subtitle;
        this.point = point;
    }

    public void addMember(Member member) {
        this.member = member;
    }
}
