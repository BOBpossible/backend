package cmc.bobpossible.store_image;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class StoreImage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "store_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String image;

    protected StoreImage() {
    }

    @Builder
    public StoreImage(Long id, Store store, Member member, String image) {
        this.id = id;
        this.store = store;
        this.member = member;
        this.image = image;
    }

    public void addStore(Store store) {
        this.store = store;
    }
}
