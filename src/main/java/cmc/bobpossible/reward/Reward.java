package cmc.bobpossible.reward;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
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
}
