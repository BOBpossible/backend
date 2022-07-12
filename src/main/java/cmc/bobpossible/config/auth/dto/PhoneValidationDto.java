package cmc.bobpossible.config.auth.dto;

import lombok.Data;

@Data
public class PhoneValidationDto {

    private String certNum;

    public PhoneValidationDto(String certNum) {
        this.certNum = certNum;
    }
}
