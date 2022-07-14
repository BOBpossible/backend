package cmc.bobpossible.question_image;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.Status;
import cmc.bobpossible.question.Question;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class QuestionImage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "question_image_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;

    protected QuestionImage() {
    }

    @Builder
    public QuestionImage(Long id, String image, Question question) {
        this.id = id;
        this.image = image;
        this.question = question;
    }

    public void addQuestion(Question question) {
        this.question = question;
    }

    public void delete() {
        this.changeStatus(Status.DELETED);
    }
}
