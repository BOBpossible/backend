package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class Address {

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_detail")
    private String detail;
}
