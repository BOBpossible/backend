package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
public class Address {

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_dong")
    private String dong;

    @Column(name = "address_x")
    private double x;

    @Column(name = "address_y")
    private double y;

    private Address() {
    }

    public Address(String street,  String dong, double x, double y) {
        this.street = street;
        this.dong = dong;
        this.x = x;
        this.y = y;
    }
}
