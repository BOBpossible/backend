package cmc.bobpossible.review.dto;

import javax.validation.constraints.NotBlank;

public class PostReviewReplyReq {

    @NotBlank
    private String content;
}
