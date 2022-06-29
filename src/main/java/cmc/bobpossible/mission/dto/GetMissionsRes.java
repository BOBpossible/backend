package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionStatus;
import lombok.Data;

@Data
public class GetMissionsRes {

    private Long missionId;
    private String storeName;
    private String storeCategory;
    private String mission;
    private int point;
    private MissionStatus missionStatus;

    public GetMissionsRes(Mission mission) {
        this.missionId = mission.getId();
        this.storeName = mission.getStore().getName();
        this.storeCategory = mission.getStore().getCategory().getName();
        this.mission = mission.getMission();
        this.point = mission.getPoint();
        this.missionStatus = mission.getMissionStatus();
    }
}
