package cmc.bobpossible.member_favorite;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.favorite.Category;
import cmc.bobpossible.member.Member;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class MemberFavorite extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favoriteId")
    private Category category;

    public static MemberFavorite create(Member member, Category category) {
        MemberFavorite memberFavorite = new MemberFavorite();
        memberFavorite.init(member, category);
        return memberFavorite;
    }

    private void init(Member member, Category category) {
        this.member = member;
        member.addMemberFavorite(this);
        this.category = category;
    }
}
