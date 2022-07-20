package cmc.bobpossible.mission;

import lombok.Data;

@Data
public class GetMissionManageCountRes {

    private int count;

    public GetMissionManageCountRes(int count) {
        this.count = count;
    }
}
