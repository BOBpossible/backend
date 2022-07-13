package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Address;
import lombok.Data;

@Data
public class GetAddressRes {

    private String addressDong;

    private String addressStreet;

    private double x;

    private double y;

    public GetAddressRes(Address address) {
        this.addressDong = address.getDong();
        this.addressStreet = address.getStreet();
        this.x = address.getX();
        this.y = address.getY();
    }
}
