package cmc.bobpossible.store;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.member.Address;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.operation_time.OperationTime;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 50)
    private String representativeMenuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<MenuImage> menuImages = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<OperationTime> operationTimes = new ArrayList<>();

    public static Store create(String storeName, Address address, Category category, int tableNum, String representativeMenuName, List<MenuImage> menuImages, List<OperationTime> operationTimes) {
        Store store = new Store();
        store.init( storeName, address, category, tableNum, representativeMenuName, menuImages, operationTimes);
        return store;
    }

    private void init(String storeName, Address address, Category category, int tableNum, String representativeMenuName, List<MenuImage> menuImages, List<OperationTime> operationTimes) {
        this.name = storeName;
        this.address = address;
        this.category = category;
        this.tableNum = tableNum;
        this.representativeMenuName = representativeMenuName;

        menuImages.forEach(this::addMenuImage);
        operationTimes.forEach(this::addOperationTime);
    }

    private void addOperationTime(OperationTime operationTime) {
        operationTimes.add(operationTime);
        operationTime.addStore(this);
    }

    private void addMenuImage(MenuImage m) {
        menuImages.add(m);
        m.addStore(this);
    }
}
