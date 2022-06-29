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
        this.storeId = mission.getStore().getId();
        this.missionId = mission.getId();
        this.point = mission.getPoint();
        this.name = mission.getStore().getName();
        this.category = mission.getStore().getCategory().getName();
        this.mission = mission.getMission();
        this.x = mission.getStore().getAddress().getX();
        this.y = mission.getStore().getAddress().getY();
    }
}
