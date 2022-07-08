package cmc.bobpossible.mission.dto;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetHome {

    private int point;
    private int rewards;
    private long dDay;
    private List<GetMissionRes> missions;

    public GetHome(Member member, List<Mission> missions) {
        this.point = member.getTotalPoints();
        this.rewards = member.getReward().getCounter();
        if (missions.size() > 0) {
            this.dDay = missions.get(0).getDoomsDay();
            this.missions = missions.stream()
                    .map(GetMissionRes::new)
                    .collect(Collectors.toList());
        }
    }
}
