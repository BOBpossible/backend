package cmc.bobpossible.Answer;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.mission_group.MissionGroup;
import cmc.bobpossible.question.Question;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Answer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;

    public void delete() {
        this.changeStatus(Status.DELETED);
    }
}
