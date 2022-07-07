package cmc.bobpossible.mission.dto;

import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.review.dto.ImageDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetMissionMapRes {

    private Long storeId;
    private Long missionId;
    private int point;
    private String name;
    private String category;
    private String mission;
    private String addressStreet;
    private String addressDetail;
    private List<ImageDto> images;

    public GetMissionMapRes(Mission mission) {
        this.storeId = mission.getMissionGroup().getStore().getId();
        this.missionId = mission.getId();
        this.point = mission.getMissionGroup().getPoint();
        this.name = mission.getMissionGroup().getStore().getName();
        this.category = mission.getMissionGroup().getStore().getCategory().getName();
        this.mission = mission.getMissionGroup().getMissionContent();
        this.addressStreet = mission.getMissionGroup().getStore().getAddress().getStreet();
        this.addressDetail = mission.getMissionGroup().getStore().getAddress().getDetail();
        if(mission.getMissionGroup().isHasImage()){
            this.images = mission.getMissionGroup().getStore().getMenuImages().stream()
                    .map(ImageDto::new)
                    .collect(Collectors.toList());
        }
    }
}
