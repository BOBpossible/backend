package cmc.bobpossible.mission;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Mission extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    @Column(length = 200)
    private String mission;

    private LocalDateTime expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(length = 20)
    private String missionStatus;
}
