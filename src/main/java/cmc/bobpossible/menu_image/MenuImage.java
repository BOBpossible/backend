package cmc.bobpossible.menu_image;

import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class MenuImage {

    @Id @GeneratedValue
    @Column(name = "menu_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @Column(columnDefinition = "TEXT")
    private String image;

    public MenuImage() {
    }

    @Builder
    public MenuImage(Long id, Store store, String image) {
        this.id = id;
        this.store = store;
        this.image = image;
    }

    public void addStore(Store store) {
        this.store = store;
    }
}
