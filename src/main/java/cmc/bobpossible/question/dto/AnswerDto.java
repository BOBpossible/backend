package cmc.bobpossible.question.dto;

import cmc.bobpossible.Answer.Answer;

public class AnswerDto {

    private String answer;

    public AnswerDto(Answer q) {
        this.answer = q.getAnswer();
    }
}
