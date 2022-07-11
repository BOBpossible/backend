package cmc.bobpossible.push;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class PushNotification extends BaseEntity  {

    @Id @GeneratedValue
    @Column(name = "push_notification_id")
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
