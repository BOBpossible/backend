package cmc.bobpossible.store.dto;

import cmc.bobpossible.member.Address;
import cmc.bobpossible.review.dto.ImageDto;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreAddress;
import cmc.bobpossible.store.StoreStatus;
import lombok.Data;

import java.util.List;

@Data
public class GetStoreRes {

    private Long storeId;
    private List<ImageDto> images;
    private String name;
    private String category;
    private StoreStatus storeStatus;
    private double averageRate;
    private StoreAddress address;
    private int reviewCount;

    public GetStoreRes(Store store) {
        this.storeId = store.getId();
        this.images = null;
        this.name = store.getName();
        this.category = store.getCategory().getName();
        this.storeStatus = store.getStoreStatus();
        this.averageRate = store.getAverageRate();
        this.address = store.getAddress();
        this.reviewCount = store.getReviewCount();
    }
}
