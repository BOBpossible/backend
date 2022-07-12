package cmc.bobpossible.question;

import cmc.bobpossible.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByMember(Member member);
}
