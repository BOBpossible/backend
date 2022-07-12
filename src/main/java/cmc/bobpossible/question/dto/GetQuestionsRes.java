package cmc.bobpossible.question.dto;

import cmc.bobpossible.question.Question;
import cmc.bobpossible.question.QuestionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetQuestionsRes {

    private String title;

    private QuestionStatus questionStatus;

    private LocalDateTime date;

    @Builder
    public GetQuestionsRes(Question question) {
        this.title = question.getTitle();
        this.questionStatus = question.getQuestionStatus();
        this.date = question.getCreateAt();
    }
}
