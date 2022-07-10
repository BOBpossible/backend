package cmc.bobpossible.review.refreshToken;

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
public class RefreshToken extends BaseEntity {

    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken() {

    }

    public void updateValue(String refreshToken) {
        this.value = refreshToken;
    }
}
