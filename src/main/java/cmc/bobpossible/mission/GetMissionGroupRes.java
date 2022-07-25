package cmc.bobpossible.mission;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class GetMissionGroupRes {

    private long missionId;

    private LocalDateTime successDate;

    private int point;

    private String phone;

    private String name;

    private DayOfWeek dayOfWeek;

    public GetMissionGroupRes(Mission mission) {
        this.missionId = mission.getId();
        this.point = mission.getMissionGroup().getPoint();
        this.phone = mission.getMember().getPhone().substring(7);
        this.name = mission.getMember().getName();

        if (mission.getMissionSuccessDate() != null) {
            this.successDate = mission.getMissionSuccessDate();
            this.dayOfWeek = successDate.getDayOfWeek();
        }
    }
}
