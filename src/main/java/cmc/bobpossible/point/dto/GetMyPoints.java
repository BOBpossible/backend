package cmc.bobpossible.point.dto;

import cmc.bobpossible.point.Point;
import lombok.Data;
import org.springframework.data.domain.Slice;

@Data
public class GetMyPoints {

    private int totalPoints;
    private Slice<PointDto> point;

    public GetMyPoints(Slice<Point> points, int totalPoints) {
        this.totalPoints = totalPoints;
        this.point = points.map(PointDto::new);
    }
}
