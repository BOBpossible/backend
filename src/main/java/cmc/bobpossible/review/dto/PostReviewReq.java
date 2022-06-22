package cmc.bobpossible.review.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostReviewReq {

    @Size(max = 300, message = "300자 초과입니다.")
    @NotNull
    private String content;
    @NotNull
    private double rate;
    @NotNull
    private Long storeId;

}
