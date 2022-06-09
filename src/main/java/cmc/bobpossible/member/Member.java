package cmc.bobpossible.member;


import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.point.Point;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
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

    private LocalDateTime birth_date;

    @Column(length = 20)
    private String phone;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();

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

    public void joinUser(String name, Gender gender, LocalDateTime birth_date, Address address, Terms terms) {
        this.role = Role.USER;
        this.name = name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.address = address;
        this.registerStatus = RegisterStatus.DONE;
        this.terms = terms;
    }
}
