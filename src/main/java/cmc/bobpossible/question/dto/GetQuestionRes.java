package cmc.bobpossible.question.dto;

import cmc.bobpossible.question.Question;
import cmc.bobpossible.question.QuestionStatus;
import cmc.bobpossible.question.dto.AnswerDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetQuestionRes {

    private String title;

    private String content;

    private LocalDateTime date;

    private List<AnswerDto> answers;

    private QuestionStatus questionStatus;

    public GetQuestionRes(Question question) {
        this.title = question.getTitle();
        this.content = question.getTitle();
        this.date = question.getCreateAt();
        this.answers = question.getAnswers().stream()
                .map(AnswerDto::new)
                .collect(Collectors.toList());
        this.questionStatus = question.getQuestionStatus();
    }
}
