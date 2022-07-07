package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionStatus;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class GetMissionsCompleteRes {

    private Long missionId;
    private Long storeId;
    private String storeName;
    private String storeCategory;
    private String mission;
    private int point;
    private LocalDateTime successDate;
    private DayOfWeek dayOfWeek;
    private MissionStatus missionStatus;

    public GetMissionsCompleteRes(Mission mission) {
        this.missionId = mission.getId();
        this.storeId = mission.getMissionGroup().getStore().getId();
        this.storeName = mission.getMissionGroup().getStore().getName();
        this.storeCategory = mission.getMissionGroup().getStore().getCategory().getName();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.point = mission.getMissionGroup().getPoint();
        this.successDate = mission.getMissionSuccessDate();
        this.dayOfWeek = mission.getMissionSuccessDate().getDayOfWeek();
        this.missionStatus = mission.getMissionStatus();
    }
}
