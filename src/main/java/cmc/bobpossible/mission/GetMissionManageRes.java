package cmc.bobpossible.mission;

import cmc.bobpossible.Status;
import cmc.bobpossible.mission_group.MissionGroup;
import lombok.Data;

@Data
public class GetMissionManageRes {

    private long missionGroupId;

    private Status missionGroupStatus;

    private String category;

    private String name;

    private String mission;

    private int point;

    public GetMissionManageRes(MissionGroup missionGroup) {
        this.missionGroupId = missionGroup.getId();
        this.missionGroupStatus = missionGroup.getStatus();
        this.category = missionGroup.getStore().getCategory().getName();
        this.name = missionGroup.getStore().getName();
        this.mission = missionGroup.getMissionContent();
        this.point = missionGroup.getPoint();
    }
}
