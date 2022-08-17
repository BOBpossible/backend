package cmc.bobpossible.mission_group;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class MissionGroup extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "mission_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private String missionContent;

    private int point;

    private boolean hasImage;

    @OneToMany(mappedBy = "missionGroup", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    protected MissionGroup() {
    }

    @Builder
    public MissionGroup( Store store, String missionContent, int point, boolean hasImage) {
        this.store = store;
        this.missionContent = missionContent;
        this.point = point;
        this.hasImage = hasImage;
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
        missions.forEach(Mission::delete);
    }

    public int getMissionsCount() {
        return this.missions.size();
    }

    public void changeMissionContent(String str) {
        this.missionContent = str;
    }
}
