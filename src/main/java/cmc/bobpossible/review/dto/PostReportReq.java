package cmc.bobpossible.review.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostReportReq {

    @NotBlank
    private String content;
}
