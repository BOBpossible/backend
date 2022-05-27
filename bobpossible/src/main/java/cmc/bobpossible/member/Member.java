package cmc.bobpossible.member;


import cmc.bobpossible.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ROLE")
@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 40)
    private String name;

    @Column(length = 10)
    private String gender;

    private LocalDateTime birth_date;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;


    private int point;
}
