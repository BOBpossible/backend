package cmc.bobpossible.point_conversion.dto;

import lombok.Data;

@Data
public class CreatePointConversion {

    private int point;

    private String name;

    private String bank;

    private String accountNumber;
}
