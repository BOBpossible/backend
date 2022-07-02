package cmc.bobpossible.store.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostStoreReq {

    @NotBlank
    private String storeName;
    @NotBlank
    @Size(max = 50)
    private String intro;
    @NotBlank
    private String addressStreet;
    @NotNull
    private double x;
    @NotNull
    private double y;
    @NotBlank
    private String addressDong;
    @NotBlank
    private String addressDetail;
    @NotNull
    private Long storeTypeId;
    @NotNull
    private int tableNum;

    @NotNull
    private String representativeMenuName;

    private List<OperationTimeVO> operationTimeVO;

}
