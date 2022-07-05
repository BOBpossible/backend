package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission.MissionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetOwnerMissionRes {

    private int missionOnProgressCount;
    private List<OwnerMissionDto> ownerMissionDto;

    @Builder
    public GetOwnerMissionRes(List<Mission> mission) {
        this.missionOnProgressCount = mission.size();
        this.ownerMissionDto = mission.stream()
                .map(OwnerMissionDto::new)
                .collect(Collectors.toList());
    }
}
