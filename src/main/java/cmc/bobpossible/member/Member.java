package cmc.bobpossible.member;


import cmc.bobpossible.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated
    private Role role;

    @Column(length = 40)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    private String email;

    @Column(length = 10)
    private String gender;

    private LocalDateTime birth_date;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private int point;

    @Enumerated
    @Column(length = 10, columnDefinition = "varchar(10) default 'NEW'")
    private RegisterStatus registerStatus;

    @Builder
    public Member(Long id, String name, String profileImage, String email) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
    }

    public Member() {

    }
}
