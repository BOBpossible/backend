package cmc.bobpossible.member.entity;


import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.*;
import cmc.bobpossible.member_category.MemberCategory;
import cmc.bobpossible.point.Point;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RegisterStatus registerStatus;

    @Embedded
    private Terms terms;

    @Builder
    public Member(Long id, String name, String profileImage, String email) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
        registerStatus = RegisterStatus.NEW;
    }

    public Member() {

    }

    public void joinUser(String name, Gender gender, LocalDate birthDate, String phone, Address address, Terms terms) {
        this.role = Role.USER;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.registerStatus = RegisterStatus.DONE;
        this.terms = terms;
    }

    public void joinOwner(String name, Gender gender, LocalDate birthDate, String phone, Terms terms) {
        this.role = Role.OWNER;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.registerStatus = RegisterStatus.DONE;
        this.terms = terms;
    }

    public void addMemberFavorite(MemberCategory memberCategory) {
        memberCategories.add(memberCategory);
    }
}