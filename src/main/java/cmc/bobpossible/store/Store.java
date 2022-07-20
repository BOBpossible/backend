package cmc.bobpossible.store;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.category.Category;
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

    @OneToOne
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
    public Store( Member member, String name, String intro, StoreAddress address, int tableNum, String representativeMenuName, Category category, List<OperationTime> operationTimes) {
        this.member = member;
        member.addStore(this);
        this.name = name;
        this.intro = intro;
        this.address = address;
        this.tableNum = tableNum;
        this.representativeMenuName = representativeMenuName;
        this.category = category;

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

        if (sum == 0) {
            return 0;
        } else {
            return sum / reviews.size();
        }
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

    public void update(String name, String intro, StoreAddress address, int tableNum, String representativeMenuName, Category category) {
        member.addStore(this);
        this.name = name;
        this.intro = intro;
        this.address = address;
        this.tableNum = tableNum;
        this.representativeMenuName = representativeMenuName;
        this.category = category;

    }

    public void deleteMenuImage(MenuImage menuImage) {
        menuImage.changeStatus(Status.DELETED);
        menuImages.remove(menuImage);
    }

    public void deleteStoreImage(StoreImage storeImage) {
        storeImage.changeStatus(Status.DELETED);
        storeImages.remove(storeImage);
    }

    public void trimAddressDong() {

        String dong = this.address.getDong();

        int target = dong.indexOf("동");

        if (target > 0) {
            this.address.changeDong( dong.substring(0, target + 1));
        }
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
        menuImages.forEach(MenuImage::delete);
        storeImages.forEach(StoreImage::delete);
        operationTimes.forEach(OperationTime::delete);
        reviews.forEach(Review::delete);
        missionGroups.forEach(MissionGroup::delete);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
