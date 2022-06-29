package cmc.bobpossible.point.dto;

import lombok.Data;

@Data
public class GetTotalPorints {

    private int totalPoints;

    public GetTotalPorints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
