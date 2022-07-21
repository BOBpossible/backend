package cmc.bobpossible.review.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostReviewReplyReq {

    @NotBlank
    private String content;
}
