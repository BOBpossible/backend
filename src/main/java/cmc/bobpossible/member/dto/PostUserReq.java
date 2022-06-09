package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class PostUserReq {

    @NotBlank
    private String name;
    @Pattern(regexp = "MALE | FEMALE | NONE", message = "MALE | FEMALE | NONE")
    private Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
