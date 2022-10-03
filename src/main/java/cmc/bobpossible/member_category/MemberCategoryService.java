package cmc.bobpossible.member_category;

import cmc.bobpossible.category.dto.GetCategoriesRes;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberCategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    @Transactional
    public void createMemberCategories(List<Long> favorites) throws BaseException {

        //고객 조회
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        for (Long favorite : favorites) {

            // 선호 음식 조회
            Category category = categoryRepository.findById(favorite)
                    .orElseThrow(() -> new BaseException(FAVORITES_ID_NOT_EXIST));

            MemberCategory memberCategory = MemberCategory.create(member, category);

            memberCategoryRepository.save(memberCategory);

        }

        member.completeRegister();
    }

    public List<GetCategoriesRes> getUserCategories() throws BaseException {

        // 고객 조회
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<MemberCategory> byMember = memberCategoryRepository.findByMember(member);
        return byMember.stream()
                .map(GetCategoriesRes::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUserCategories(Long memberCategoryId) throws BaseException {

        MemberCategory memberCategory = memberCategoryRepository.findById(memberCategoryId)
                .orElseThrow(() -> new BaseException(MEMBER_CATEGORY_NOT_EXISTS));

        memberCategory.delete();
    }
}
