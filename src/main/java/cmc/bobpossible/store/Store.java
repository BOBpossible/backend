package cmc.bobpossible.store;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.member.Address;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.mission_group.MissionGroup;
import cmc.bobpossible.operation_time.OperationTime;
import cmc.bobpossible.review.Review;
import cmc.bobpossible.store_image.StoreImage;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private StoreAddress address;

    private int tableNum;

    @Column(length = 50)
    private String representativeMenuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<MenuImage> menuImages = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreImage> storeImages = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<OperationTime> operationTimes = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<MissionGroup> missionGroups = new ArrayList<>();

    protected Store() {
    }

    @Builder
    public Store( Member member, String name, String intro, StoreAddress address, int tableNum, String representativeMenuName, Category category, List<OperationTime> operationTimes, List<Review> reviews) {
        this.member = member;
        this.name = name;
        this.intro = intro;
        this.address = address;
        this.tableNum = tableNum;
        this.representativeMenuName = representativeMenuName;
        this.category = category;
//        this.menuImages = menuImages;
//        this.operationTimes = operationTimes;
        operationTimes.forEach(this::addOperationTime);
        this.reviews = reviews;
    }

//    public static Store create(Member member , String storeName, String intro, StoreAddress address, Category category, int tableNum, String representativeMenuName, List<MenuImage> menuImages, List<OperationTime> operationTimes) {
//        Store store = new Store();
//        store.init( member, storeName, intro, address, category, tableNum, representativeMenuName, menuImages, operationTimes);
//        return store;
//    }
//
//    private void init(Member member, String storeName, String intro, StoreAddress address, Category category, int tableNum, String representativeMenuName, List<MenuImage> menuImages, List<OperationTime> operationTimes) {
//        this.member = member;
//        this.name = storeName;
//        this.intro = intro;
//        this.address = address;
//        this.category = category;
//        this.tableNum = tableNum;
//        this.representativeMenuName = representativeMenuName;
//
//        menuImages.forEach(this::addMenuImage);
//        operationTimes.forEach(this::addOperationTime);
//    }

    private void addOperationTime(OperationTime operationTime) {
        operationTimes.add(operationTime);
        operationTime.addStore(this);
    }

    private void addMenuImage(MenuImage m) {
        menuImages.add(m);
        m.addStore(this);
    }

    private void addStoreImage(StoreImage m) {
        storeImages.add(m);
        m.addStore(this);
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public double getAverageRate() {
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRate();
        }

        return sum / reviews.size();
    }

    // 현재 영업중인지
    public StoreStatus getStoreStatus() {
        LocalDateTime now = LocalDateTime.now();
        for (OperationTime operationTime : operationTimes) {
            // 요일
            if(operationTime.getDayOfWeek() == now.getDayOfWeek()){
                // 현재 영업 시간
                if (operationTime.getStartTime().isBefore(LocalTime.from(now)) && operationTime.getEndTime().isAfter(LocalTime.from(now))) {
                    // 휴식시간
                    if (operationTime.getBreakStartTime().isBefore(LocalTime.from(now)) && operationTime.getBreakEndTime().isAfter(LocalTime.from(now))) {
                        return StoreStatus.BREAK;
                    }
                    return StoreStatus.OPEN;
                }
                return StoreStatus.CLOSED;
            }
        }
        return StoreStatus.CLOSED;
    }

    public void deleteReview(Review review) {
        this.reviews.remove(review);
    }

    public void addMenuImages(List<MenuImage> menuImages) {
        menuImages.forEach(this::addMenuImage);
    }

    public void addStoreImages(List<StoreImage> storeImages) {
        storeImages.forEach(this::addStoreImage);
    }
}
