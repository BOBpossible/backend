package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Gender;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class PostUserReq {

    @NotBlank
    private String name;
    @Pattern(regexp = "MALE | FEMALE | NONE", message = "MALE | FEMALE | NONE")
    private Gender gender;
    @Pattern(regexp = "^(19|20)\\d{2}-\\d{2}-\\d{2}$", message = "형식 (19xx-xx-xx or 20xx-xx-xx)가 아닙니다 ")
    private LocalDateTime birthDate;
    @NotBlank
    private String addressStreet;
    @NotBlank
    private String addressDetail;

    //이용약관
    @AssertTrue
    private Boolean termsOfService;
    @AssertTrue
    private Boolean privacyPolicy;
    @NotNull
    private Boolean locationInfo;
    @NotNull
    private Boolean marketing;
}
