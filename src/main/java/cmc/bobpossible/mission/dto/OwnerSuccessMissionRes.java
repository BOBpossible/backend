package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class OwnerSuccessMissionRes {
    private Long missionId;
    private String userName;
    private Long userId;
    private String mission;
    private int point;
    private LocalDateTime date;
    private DayOfWeek dayOfWeek;

    protected OwnerSuccessMissionRes() {
    }

    @Builder
    public OwnerSuccessMissionRes(Mission mission) {
        this.missionId = mission.getId();
        this.userName = mission.getMember().getName();
        this.userId = mission.getMember().getId();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.point = mission.getMissionGroup().getPoint();
        this.date = mission.getUpdateAt();
        this.dayOfWeek = mission.getUpdateAt().getDayOfWeek();
    }
}
