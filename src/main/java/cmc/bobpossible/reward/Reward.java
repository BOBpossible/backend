package cmc.bobpossible.reward;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
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

            //0으로 초기화
            counter = 0;
        }
    }
}
