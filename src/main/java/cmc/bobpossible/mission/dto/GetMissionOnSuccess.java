package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class GetMissionOnSuccess {

    private Long missionId;
    private String userName;
    private Long userId;
    private String mission;
    private int point;
    private long seconds;

    @Builder
    public GetMissionOnSuccess(Mission mission) {
        this.missionId = mission.getId();
        this.userName = mission.getMember().getName();
        this.userId = mission.getMember().getId();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.point = mission.getMissionGroup().getPoint();
        this.seconds = Duration.between(mission.getUpdateAt(), LocalDateTime.now()).toSeconds();
    }

    protected GetMissionOnSuccess() {
    }
}
