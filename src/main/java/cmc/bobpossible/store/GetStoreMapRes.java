package cmc.bobpossible.store;

import lombok.Builder;
import lombok.Data;

@Data
public class GetStoreMapRes {


    private Long storeId;
    private int point;
    private String imageUrl;
    private String name;
    private String category;
    private double distance;
    private double x;
    private double y;
    private boolean isMission;

    @Builder
    public GetStoreMapRes(Long storeId, int point, String imageUrl, String name, String category, double distance, double x, double y, boolean isMission) {
        this.storeId = storeId;
        this.point = point;
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.x = x;
        this.y = y;
        this.isMission = isMission;
    }
}
