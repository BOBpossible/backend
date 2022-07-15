package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class PostOwnerReq {

    @NotBlank
    private String name;
    @NotNull
    private Gender gender;

    private String phone;

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
