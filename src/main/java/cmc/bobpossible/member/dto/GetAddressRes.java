package cmc.bobpossible.member.dto;

import cmc.bobpossible.member.Address;
import lombok.Data;

@Data
public class GetAddressRes {

    private String dong;

    private String street;

    public GetAddressRes(Address address) {
        this.dong = address.getDong();
        this.street = address.getStreet();
    }
}
