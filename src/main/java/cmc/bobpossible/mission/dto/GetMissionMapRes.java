package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import lombok.Data;

@Data
public class GetMissionMapRes {

    private Long storeId;
    private Long missionId;
    private int point;
    private String name;
    private String category;
    private String mission;
    private double x;
    private double y;

    public GetMissionMapRes(Mission mission) {
        this.storeId = mission.getMissionGroup().getStore().getId();
        this.missionId = mission.getId();
        this.point = mission.getMissionGroup().getPoint();
        this.name = mission.getMissionGroup().getStore().getName();
        this.category = mission.getMissionGroup().getStore().getCategory().getName();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.x = mission.getMissionGroup().getStore().getAddress().getX();
        this.y = mission.getMissionGroup().getStore().getAddress().getY();
    }
}
