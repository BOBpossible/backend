package cmc.bobpossible.reward;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.point.Point;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Reward extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reward_id")
    private Long id;

    private int counter;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected Reward() {
    }

    @Builder
    public Reward(int counter) {
        this.counter = counter;
    }

    public void addCounter() {
        counter++;
        if(counter == 10){
            //포인트 1000 추가
            member.addPoint(Point.builder()
                    .point(1000)
                    .title("리워드")
                    .subtitle("미션 10개 달성")
                    .build());
            //0으로 초기화
            counter = 0;
        }
    }

    public void addMember(Member member) {
        this.member = member;
    }
}
