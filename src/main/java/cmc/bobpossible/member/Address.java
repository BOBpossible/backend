package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
public class Address {

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_detail")
    private String detail;

    @Column(name = "address_dong")
    private String dong;

    private Address() {
    }

    public Address(String street, String detail, String dong) {
        this.street = street;
        this.detail = detail;
        this.dong = dong;
    }
}
