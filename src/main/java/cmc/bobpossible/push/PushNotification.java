package cmc.bobpossible.push;

import cmc.bobpossible.BaseEntity;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class PushNotification extends BaseEntity  {

    @Id @GeneratedValue
    @Column(name = "push_notification_id")
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String title;

    private String storeName;

    private String subTitle;

    @Enumerated(EnumType.STRING)
    private PushType pushType;

    private boolean checked;

    protected PushNotification() {
    }

    @Builder
    public PushNotification(Long id, Member member, String title, String storeName, String subTitle, PushType pushType, boolean checked) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.storeName = storeName;
        this.subTitle = subTitle;
        this.pushType = pushType;
        this.checked = checked;
    }

    public PushNotification createReviewPush(Member member, Store store) {
        return PushNotification.builder()
                .title("리뷰를 남겨주세요.")
                .member(member)
                .storeName(store.getName())
                .subTitle("의 음식이 맛있었다면 리뷰를 남겨주세요.")
                .checked(false)
                .pushType(PushType.REVIEW)
                .build();
    }

    public PushNotification createNewMissionPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .title("새로운 미션이 도착했습니다.")
                .member(member)
                .storeName(store.getName())
                .subTitle("에서 "+mission.getMissionGroup().getMissionContent()+"의 식사를 하세요!")
                .checked(false)
                .pushType(PushType.REVIEW)
                .build();
    }

    public void check() {
        this.checked = true;
    }
}
