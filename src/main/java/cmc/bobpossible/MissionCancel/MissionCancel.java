package cmc.bobpossible.MissionCancel;

import cmc.bobpossible.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class MissionCancel extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    private String reason;

    protected MissionCancel() {
    }

    @Builder
    public MissionCancel(Long id, String reason) {
        this.id = id;
        this.reason = reason;
    }
}
