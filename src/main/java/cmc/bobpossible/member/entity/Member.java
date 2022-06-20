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
<<<<<<< HEAD
    public Member(Long id, String name, String email) {
        this.id = id;
        this.name = name;
=======
    public Member(Long id, String name, String profileImage, String email) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
>>>>>>> 1512c441780c7c79413647d4d638da0dd153e401
        this.email = email;
        registerStatus = RegisterStatus.NEW;
    }

    public Member() {

    }

<<<<<<< HEAD
    public static Member create(String email, String name) {
        Member member = new Member();
        member.init(email, name);
        return member;
    }

    private void init(String email, String name) {
        this.email = email;
        this.name = name;
        registerStatus = RegisterStatus.NEW;
    }

=======
>>>>>>> 1512c441780c7c79413647d4d638da0dd153e401
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
