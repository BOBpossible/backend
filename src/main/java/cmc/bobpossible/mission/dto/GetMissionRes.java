package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class GetMissionRes {

    private Long missionId;
    private String storeName;
    private String storeCategory;
    private String mission;
    private MissionStatus missionStatus;
    private int point;

    @Builder
    public GetMissionRes(Mission mission) {
        this.missionId = mission.getId();
        this.storeName = mission.getMissionGroup().getStore().getName();
        this.storeCategory = mission.getMissionGroup().getStore().getCategory().getName();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.point = mission.getMissionGroup().getPoint();
        this.missionStatus = mission.getMissionStatus();
    }
}
