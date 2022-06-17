package cmc.bobpossible.store.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostStoreReq {

    @NotBlank
    private String name;
    @NotBlank
    private String addressStreet;
    @NotBlank
    private String addressDetail;
    @NotBlank
    private String addressDong;
    @NotNull
    private Long storeTypeId;
    @NotNull
    private int tableNum;
}
