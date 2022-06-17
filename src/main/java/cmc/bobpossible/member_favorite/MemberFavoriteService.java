package cmc.bobpossible.member_favorite;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.favorite.Favorite;
import cmc.bobpossible.favorite.FavoriteRepository;
import cmc.bobpossible.member.Member;
import cmc.bobpossible.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberFavoriteService {

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final MemberFavoriteRepository memberFavoriteRepository;

    @Transactional
    public void createMemberFavorites(Long favoriteId) throws BaseException {

        //고객 조회
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        // 선호 음식 조회
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new BaseException(FAVORITES_ID_NOT_EXIST));

        MemberFavorite memberFavorite = MemberFavorite.create(member, favorite);

        memberFavoriteRepository.save(memberFavorite);
    }
}
