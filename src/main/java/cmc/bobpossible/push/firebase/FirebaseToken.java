package cmc.bobpossible.push.firebase;

import cmc.bobpossible.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class FirebaseToken  extends BaseEntity {

    @Id
    @Column(name = "fb_key")
    private Long key;

    @Column(name = "fb_value")
    private String value;

    protected FirebaseToken() {
    }

    @Builder
    public FirebaseToken(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public void update(String value) {
        this.value = value;
    }
}
