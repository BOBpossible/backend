package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import lombok.Builder;
import lombok.Data;

@Data
public class OwnerMissionDto {
    private Long missionId;
    private String userName;
    private Long userId;
    private String mission;
    private int point;
    private int startHour;
    private int startMinute;

    protected OwnerMissionDto() {
    }

    @Builder
    public OwnerMissionDto(Mission mission) {
        this.missionId = mission.getId();
        this.userName = mission.getMember().getName();
        this.userId = mission.getMember().getId();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.point = mission.getMissionGroup().getPoint();
        this.startHour = mission.getUpdateAt().getHour();
        this.startMinute = mission.getUpdateAt().getMinute();
    }
}
