package cmc.bobpossible.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressDto {
    @NotBlank
    private String addressStreet;
    @NotBlank
    private String addressDong;
    @NotNull
    private double x;
    @NotNull
    private double y;
}
