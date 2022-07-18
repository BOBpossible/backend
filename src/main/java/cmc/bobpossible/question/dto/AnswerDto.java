package cmc.bobpossible.question.dto;

import cmc.bobpossible.Answer.Answer;
import lombok.Data;

@Data
public class AnswerDto {

    private String answer;

    public AnswerDto(Answer q) {
        this.answer = q.getAnswer();
    }
}
