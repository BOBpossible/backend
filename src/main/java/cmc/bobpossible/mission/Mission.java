package cmc.bobpossible.mission;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

import static cmc.bobpossible.mission.MissionStatus.NEW;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Mission extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    String mission;

    int point;

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

    public boolean checkValidation() {
        if(expiredDate.isBefore(LocalDateTime.now())){
            this.changeStatus(Status.DELETED);
            return true;
        }
        return false;
    }

    protected Mission() {
    }

    @Builder
    public Mission( LocalDateTime expiredDate, Store store, Member member, MissionStatus missionStatus) {
        this.mission = "10,000원 이상의 식사시";
        this.point = 500;
        this.expiredDate = LocalDateTime.now().plusDays(7);
        this.store = store;
        this.member = member;
        member.addMission(this);
        this.missionStatus = NEW;
    }

    public long getDoomsDay() {
        return Duration.between(expiredDate, LocalDateTime.now()).toDays();
    }
}
