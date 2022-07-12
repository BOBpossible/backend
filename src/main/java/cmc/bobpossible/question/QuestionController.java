package cmc.bobpossible.question;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.question.dto.GetQuestionRes;
import cmc.bobpossible.question.dto.GetQuestionsRes;
import cmc.bobpossible.question.dto.PostQuestion;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation("1대1 문의하기")
    @PostMapping("")
    public BaseResponse<String> postQuestion(@Validated @RequestBody PostQuestion postQuestion, Errors errors) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        questionService.postQuestion(postQuestion);
        return new BaseResponse<>("");
    }

    @ApiOperation("1대1 문의 이미지 등록")
    @PostMapping("/images/{questionId}")
    public BaseResponse<String> createQuestionImage(@RequestPart List<MultipartFile> questionImage, @PathVariable Long questionId) throws BaseException, IOException {

        questionService.createQuestionImage(questionImage, questionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("1대1 문의 내역")
    @PostMapping("/me")
    public BaseResponse<List<GetQuestionsRes>> getQuestions() throws BaseException {

        return new BaseResponse<>( questionService.getQuestions());
    }

    @ApiOperation("1대1 문의 상세조회")
    @PostMapping("/{questionId}")
    public BaseResponse<GetQuestionRes> getQuestion(@PathVariable Long questionId) throws BaseException {

        return new BaseResponse<>( questionService.getQuestion(questionId));
    }
}
