package cmc.bobpossible.store.dto;

import cmc.bobpossible.store.Store;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetOwnerStoreRes {

    private String storeName;

    private String intro;

    private String addressStreet;

    private double x;

    private double y;

    private String addressDong;

    private String addressDetail;

    private Long storeTypeId;

    private int tableNum;

    private String representativeMenuName;

    public GetOwnerStoreRes(Store store) {
        this.storeName = store.getName();
        this.intro = store.getIntro();
        this.addressStreet = store.getAddress().getStreet();
        this.x = store.getAddress().getX();
        this.y = store.getAddress().getY();
        this.addressDong = store.getAddress().getDong();
        this.addressDetail = store.getAddress().getDetail();
        this.storeTypeId = store.getCategory().getId();
        this.tableNum = store.getTableNum();
        this.representativeMenuName = store.getRepresentativeMenuName();

    }
}
