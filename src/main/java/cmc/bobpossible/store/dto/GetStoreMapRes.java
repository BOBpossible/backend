package cmc.bobpossible.store.dto;

import cmc.bobpossible.mission.Mission;
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
    private String addressStreet;
    private String addressDetail;
    private boolean isMission;

    protected GetStoreMapRes() {
    }

    @Builder
    public GetStoreMapRes(Long storeId, int point, String imageUrl, String name, String category, double distance, String addressStreet, String addressDetail, boolean isMission) {
        this.storeId = storeId;
        this.point = point;
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.addressStreet = addressStreet;
        this.addressDetail = addressDetail;
        this.isMission = isMission;
    }

    public void changeToHasMission(Mission mission) {
        this.isMission = true;
        this.point = mission.getMissionGroup().getPoint();
    }
}
