package cmc.bobpossible.question;

import cmc.bobpossible.Answer.Answer;
import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.question_image.QuestionImage;
import cmc.bobpossible.review_image.ReviewImage;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Question extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionImage> questionImages = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    protected Question() {
    }

    @Builder
    public Question(Long id, String title, String content, QuestionStatus questionStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.questionStatus = QuestionStatus.WAITING;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void addQuestionImages(List<QuestionImage> questionImages) {
        questionImages.forEach(this::addQuestionImage);
    }

    private void addQuestionImage(QuestionImage questionImage) {
        questionImage.addQuestion(this);
        this.questionImages.add(questionImage);
    }
}
