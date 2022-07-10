package cmc.bobpossible.mission;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission_group.MissionGroup;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cmc.bobpossible.mission.MissionStatus.NEW;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Mission extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    private LocalDateTime expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MissionStatus missionStatus;

    private LocalDateTime missionSuccessDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionGroupId")
    private MissionGroup missionGroup;

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
    public Mission(Member member, MissionGroup missionGroup ) {
        this.expiredDate = LocalDateTime.now().plusDays(8).minusSeconds(1);
        this.member = member;
        member.addMission(this);
        this.missionStatus = NEW;
        this.missionGroup = missionGroup;
        missionGroup.addMission(this);
    }

    public long getDoomsDay() {
        return Duration.between( LocalDateTime.now(), expiredDate).toDays();
    }

    public void challengeMission() {
        this.missionStatus = MissionStatus.PROGRESS;
    }
}
