package cmc.bobpossible.store;

import lombok.Data;

import javax.persistence.Column;

@Data
public class StoreAddress {

    @Column(name = "store_address_street")
    private String street;

    @Column(name = "store_address_detail")
    private String detail;

    @Column(name = "store_address_dong")
    private String dong;

    @Column(name = "store_address_x")
    private double x;

    @Column(name = "store_address_y")
    private double y;

    protected StoreAddress() {
    }

    public StoreAddress(String street, String detail, String dong, double x, double y) {
        this.street = street;
        this.detail = detail;
        this.dong = dong;
        this.x = x;
        this.y = y;
    }

    public void changeDong(String dong) {
        this.dong = dong;
    }
}
