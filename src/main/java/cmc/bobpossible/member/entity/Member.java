package cmc.bobpossible.member.entity;


import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.*;
import cmc.bobpossible.member_category.MemberCategory;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.point.Point;
import cmc.bobpossible.reward.Reward;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store_authentication.StoreAuthentication;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 40)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Column(length = 20)
    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private RegisterStatus registerStatus;

    @Embedded
    private Terms terms;

    @Embedded
    private Notification notification;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StoreAuthentication> storeAuthentications = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Store store;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Reward reward;

    @Builder
    public Member(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        registerStatus = RegisterStatus.NEW;
    }

    protected Member() {

    }

    public static Member create(String email, String name) {

        return Member.builder()
                        .email(email)
                        .name(name)
                        .build();
    }

    public void joinUser(String name, Gender gender, LocalDate birthDate, String phone, Address address, Terms terms) {
        this.role = Role.USER;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.terms = terms;
        this.notification.init();

        //리워드 생성
        Reward reward = Reward.builder()
                .counter(0)
                .build();

        reward.addMember(this);
        this.reward = reward;
    }

    public void joinOwner(String name, Gender gender, LocalDate birthDate, String phone, Terms terms) {
        this.role = Role.OWNER;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.registerStatus = RegisterStatus.JOIN;
        this.terms = terms;
    }

    public void addMemberFavorite(MemberCategory memberCategory) {
        memberCategories.add(memberCategory);
    }

    public void completeRegister() {
        this.registerStatus = RegisterStatus.DONE;
    }

    public void addMission(Mission mission) {
        missions.add(mission);
    }

    public int getTotalPoints() {

        int sum = 0;

        for (Point point : points) {
            sum += point.getPoint();
        }

        return sum;
    }

    public void changeImage(String image) {
        this.profileImage = image;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addStore(Store store) {
        this.store = store;
    }

    public void addStoreAuthenticationImages(List<StoreAuthentication> storeAuthenticationImage) {
        storeAuthenticationImage.forEach(this::addStoreAuthenticationImage);
    }

    public void addStoreAuthenticationImage(StoreAuthentication storeAuthentication) {
        storeAuthentication.addMember(this);
        storeAuthentications.add(storeAuthentication);
    }

    public void addPoint(Point point) {
        point.addMember(this);
        this.points.add(point);
    }

    public void trimAddressDong() {

        String dong = this.address.getDong();

        int target = dong.indexOf("동");

        if (target > 0) {
            this.address.changeDong( dong.substring(0, target + 1));
        }
    }

    public void updateUserNotification(Boolean event, Boolean question, Boolean review) {
        this.notification.update(event, question, review);
    }
}
