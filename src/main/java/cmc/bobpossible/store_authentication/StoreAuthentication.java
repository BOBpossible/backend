package cmc.bobpossible.store_authentication;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class StoreAuthentication extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "store_authentication_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Builder
    public StoreAuthentication( Member member, String image) {
        this.member = member;
        this.image = image;
    }

    protected StoreAuthentication() {
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
    }
}
