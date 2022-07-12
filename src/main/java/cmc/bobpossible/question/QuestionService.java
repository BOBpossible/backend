package cmc.bobpossible.question;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.question.dto.GetQuestionRes;
import cmc.bobpossible.question.dto.GetQuestionsRes;
import cmc.bobpossible.question.dto.PostQuestion;
import cmc.bobpossible.question_image.QuestionImage;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final S3Uploader s3Uploader;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void postQuestion(PostQuestion postQuestion) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Question question = Question.builder()
                .title(postQuestion.getTitle())
                .content(postQuestion.getContent())
                .build();

        member.addQuestion(question);

    }

    public List<GetQuestionsRes> getQuestions() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Question> questions = questionRepository.findByMember(member);

        return questions.stream()
                .map(GetQuestionsRes::new)
                .collect(Collectors.toList());
    }

    public void createQuestionImage(List<MultipartFile> questionImage, Long questionId) throws BaseException, IOException {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BaseException(INVALID_QUESTION_ID));

        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < questionImage.size() && i < 3; i++) {
            imageURL.add( s3Uploader.upload(questionImage.get(i), "menuImage"));
        }

        // 메뉴 이미지 엔티티
        List<QuestionImage> questionImages = imageURL.stream()
                .map(i -> QuestionImage.builder().image(i).question(question).build())
                .collect(Collectors.toList());

        question.addQuestionImages(questionImages);
    }

    public GetQuestionRes getQuestion(Long questionId) throws BaseException {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BaseException(INVALID_QUESTION_ID));

        return new GetQuestionRes(question);
    }
}
