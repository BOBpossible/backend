package cmc.bobpossible.member_category;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.category.Category;
import cmc.bobpossible.member.Member;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class MemberCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favoriteId")
    private Category category;

    public static MemberCategory create(Member member, Category category) {
        MemberCategory memberCategory = new MemberCategory();
        memberCategory.init(member, category);
        return memberCategory;
    }

    private void init(Member member, Category category) {
        this.member = member;
        member.addMemberFavorite(this);
        this.category = category;
    }
}
