package cmc.bobpossible.search;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.utils.DistanceCalculator;
import lombok.Data;

@Data
public class SearchRes {

    private Long storeId;
    private String name;
    private String category;
    private double distance;

    public SearchRes(Member member,Store store) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.category = store.getCategory().getName();
        this.distance = DistanceCalculator.distance(member.getAddress().getX(), member.getAddress().getY(), store.getAddress().getX(), store.getAddress().getY());
    }
}
