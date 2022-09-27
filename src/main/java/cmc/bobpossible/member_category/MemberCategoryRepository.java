package cmc.bobpossible.member_category;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
    List<MemberCategory> findByMember(Member member);
}
