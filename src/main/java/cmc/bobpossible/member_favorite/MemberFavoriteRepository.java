package cmc.bobpossible.member_favorite;

import cmc.bobpossible.favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFavoriteRepository extends JpaRepository<MemberFavorite, Long> {
}
