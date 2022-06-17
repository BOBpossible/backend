package cmc.bobpossible.member_category;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.member.Member;
import cmc.bobpossible.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberCategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    @Transactional
    public void createMemberCategories(Long favoriteId) throws BaseException {

        //고객 조회
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        // 선호 음식 조회
        Category category = categoryRepository.findById(favoriteId)
                .orElseThrow(() -> new BaseException(FAVORITES_ID_NOT_EXIST));

        MemberCategory memberCategory = MemberCategory.create(member, category);

        memberCategoryRepository.save(memberCategory);
    }
}
