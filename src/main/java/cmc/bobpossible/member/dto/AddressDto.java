package cmc.bobpossible.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDto {
    @NotBlank
    private String addressStreet;
    @NotBlank
    private String addressDong;
}
