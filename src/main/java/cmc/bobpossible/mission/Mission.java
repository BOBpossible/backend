package cmc.bobpossible.mission;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
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

    int missionPrice;

    int reward;

    private LocalDateTime expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MissionStatus missionStatus;

    public void requestComplete() {
        this.missionStatus = MissionStatus.OWNER_CHECK;
    }

    public void checkValidation() {
        if(expiredDate.isBefore(LocalDateTime.now())){
            this.changeStatus(Status.DELETED);
        }
    }
}
