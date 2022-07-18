package cmc.bobpossible.mission;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMissionGroupRes {

    private long missionId;

    private LocalDateTime successDate;

    private int point;

    private String phone;

    public GetMissionGroupRes(Mission mission) {
        this.missionId = mission.getId();
        this.successDate = mission.getMissionSuccessDate();
        this.point = mission.getMissionGroup().getPoint();
        this.phone = mission.getMember().getPhone().substring(7);
    }
}
