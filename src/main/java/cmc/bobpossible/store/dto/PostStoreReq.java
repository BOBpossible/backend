package cmc.bobpossible.store.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PostStoreReq {

    @NotBlank
    private String storeName;
    @NotBlank
    private String addressStreet;
    @NotNull
    private double x;
    @NotNull
    private double y;
    @NotBlank
    private String addressDong;
    @NotNull
    private Long storeTypeId;
    @NotNull
    private int tableNum;

    @NotNull
    private String representativeMenuName;

    private List<OperationTimeVO> operationTimeVO;

}
