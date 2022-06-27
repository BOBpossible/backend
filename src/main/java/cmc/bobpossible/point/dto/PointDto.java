package cmc.bobpossible.point.dto;

import cmc.bobpossible.point.Point;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PointDto {

    private LocalDateTime date;
    private String title;
    private String subTitle;
    private int point;

    public PointDto(Point point) {
        this.date = point.getUpdateAt();
        this.title = point.getTitle();
        this.subTitle = point.getSubtitle();
        this.point = point.getPoint();
    }
}
