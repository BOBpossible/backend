package cmc.bobpossible.question_image;

import cmc.bobpossible.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionImageRepository extends JpaRepository<Question, Long> {
}
