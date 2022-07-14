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

import static cmc.bobpossible.mission.MissionStatus.DONE;
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

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    private Boolean onProgress;

    private LocalDateTime missionSuccessDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionGroupId")
    private MissionGroup missionGroup;

    public void requestComplete() {
        this.missionStatus = MissionStatus.CHECKING;
    }

    public boolean checkValidation() {
        if(expiredDate.isBefore(LocalDateTime.now())){
            changeStatus(Status.EXPIRED);
            return true;
        }
        return false;
    }

    protected Mission() {
    }

    @Builder
    public Mission(Member member, MissionGroup missionGroup ) {
        this.expiredDate = LocalDateTime.now().plusDays(7);
        this.member = member;
        member.addMission(this);
        this.missionStatus = NEW;
        this.onProgress = false;
        this.missionGroup = missionGroup;
        missionGroup.addMission(this);

        this.reviewStatus = ReviewStatus.NEW;
    }

    public long getDoomsDay() {
        return Duration.between( LocalDateTime.now(), expiredDate).toDays();
    }

    public void challengeMission() {
        this.missionStatus = MissionStatus.PROGRESS;
        this.onProgress = true;
    }

    public void cancelMission() {
        this.missionStatus = NEW;
        this.onProgress = false;
    }

    public void successMission() {
        this.missionSuccessDate = LocalDateTime.now();
        this.missionStatus = DONE;
        this.onProgress = false;
    }

    public void reviewDone() {
        this.reviewStatus = ReviewStatus.DONE;
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
    }
}
