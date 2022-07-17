package cmc.bobpossible.member;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

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

    public void changeAddress(String addressDong, String addressStreet, double x, double y) {
        this.street = addressStreet;
        this.dong = addressDong;
        this.x = x;
        this.y = y;
    }

    public void changeDong(String dong) {
        this.dong = dong;
    }
}
