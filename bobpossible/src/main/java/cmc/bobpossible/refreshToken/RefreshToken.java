package cmc.bobpossible.refreshToken;

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
    @Column
    private String key;

    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String refreshToken) {
        this.value = refreshToken;
    }
}
