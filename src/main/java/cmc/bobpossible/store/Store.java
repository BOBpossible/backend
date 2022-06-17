package cmc.bobpossible.store;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.member.Address;
import cmc.bobpossible.member.entity.Member;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Store extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(length = 100)
    private String name;

    @Column(length = 50)
    private String intro;

    @Embedded
    private Address address;

    private int tableNum;

    @Embedded
    private RepresentativeMenu representativeMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    public static Store create(@NotBlank String storeName, Address address, Category category, @NotNull int tableNum, RepresentativeMenu representativeMenu) {
        Store store = new Store();
        store.init( storeName, address, category, tableNum, representativeMenu);
        return store;
    }

    private void init(String storeName, Address address, Category category, int tableNum, RepresentativeMenu representativeMenu) {
        this.name = storeName;
        this.address = address;
        this.category = category;
        this.tableNum = tableNum;
        this.representativeMenu = representativeMenu;
    }
}
