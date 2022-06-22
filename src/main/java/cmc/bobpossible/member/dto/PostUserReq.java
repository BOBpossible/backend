package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostUserReq {

    @NotBlank
    private String name;
    @NotNull
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate;
    @NotBlank
    private String addressStreet;
    @NotBlank
    private String addressDong;
    @NotNull
    private double x;
    @NotNull
    private double y;
    @Pattern(regexp = "^\\d{3}+\\d{4}+\\d{4}$", message = "휴대폰 번호 형식이 아님 01xyyyyzzzz(하이픈 없이)")
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
