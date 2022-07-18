package cmc.bobpossible.mission;

import cmc.bobpossible.Status;
import cmc.bobpossible.mission_group.MissionGroup;
import lombok.Data;

@Data
public class GetMissionManageRes {

    private long missionGroupId;

    private Status missionGroupStatus;

    private String category;

    public GetMissionManageRes(MissionGroup missionGroup) {
        this.missionGroupId = missionGroup.getId();
        this.missionGroupStatus = missionGroup.getStatus();
        this.category = missionGroup.getStore().getCategory().getName();
    }
}
