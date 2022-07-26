package cmc.bobpossible.store.dto;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.utils.DistanceCalculator;
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
    private double userX;
    private double userY;
    private double rate;

    protected GetStoreMapRes() {
    }

    @Builder
    public GetStoreMapRes(Long storeId, int point, String imageUrl, String name, String category, double distance, String addressStreet, String addressDetail, boolean isMission, double userX, double userY, double rate) {
        this.storeId = storeId;
        this.point = point;
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.addressStreet = addressStreet;
        this.addressDetail = addressDetail;
        this.isMission = isMission;
        this.userX = userX;
        this.userY = userY;
        this.rate = rate;
    }

    public GetStoreMapRes(Member member, Store store) {
        this.storeId = store.getId();
        this.imageUrl = store.getStoreImages().get(0).getImage();
        this.isMission = false;
        this.name = store.getName();
        this.category = store.getCategory().getName();
        this.distance = DistanceCalculator.distance(member.getAddress().getX(), member.getAddress().getY(), store.getAddress().getX(), store.getAddress().getY());
        this.addressStreet = store.getAddress().getStreet();
        this.addressDetail = store.getAddress().getDetail();
        this.userX = member.getAddress().getX();
        this.userY = member.getAddress().getY();
        this.rate = store.getAverageRate();
    }

    public void changeToHasMission(Mission mission) {
        this.isMission = true;
        this.point = mission.getMissionGroup().getPoint();
    }
}
