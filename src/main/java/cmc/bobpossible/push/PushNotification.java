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

    private String subTitle;

    private String name;

    @Enumerated(EnumType.STRING)
    private PushType pushType;

    private boolean checked;

    private Long subId;

    protected PushNotification() {
    }

    @Builder
    public PushNotification(Long id, Member member, String title, String subTitle, String name, PushType pushType, boolean checked, Long subId) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.subTitle = subTitle;
        this.name = name;
        this.pushType = pushType;
        this.checked = checked;
        this.subId = subId;
    }

    public static PushNotification createReviewPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(member)
                .name(store.getName())
                .title("리뷰를 남겨주세요!")
                .subTitle("의 음식이 어땠는지 알려주세요")
                .checked(false)
                .pushType(PushType.REVIEW)
                .subId(mission.getId())
                .build();
    }

    public static PushNotification createNewMissionPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(member)
                .name(store.getName())
                .title("새로운 미션이 도착했습니다!")
                .subTitle("에서 "+mission.getMissionGroup().getMissionContent()+"의 식사를 하세요!")
                .checked(false)
                .pushType(PushType.REVIEW)
                .subId(mission.getId())
                .build();
    }

    public static PushNotification createMissionSuccessPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(member)
                .name(store.getName())
                .title("성공요청이 수락되었습니다.")
                .subTitle("성공버튼을 눌러 미션을 완료해주세요!")
                .checked(false)
                .pushType(PushType.MISSION_SUCCESS)
                .subId(mission.getId())
                .build();
    }

    public static PushNotification createMissionDeniedPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(member)
                .name(store.getName())
                .title("미션을 실패했습니다!")
                .subTitle("성공요청이 거절되었습니다. 문제시 '1대1문의'를 이용해주세요.")
                .checked(false)
                .pushType(PushType.MISSION_DENIED)
                .subId(mission.getId())
                .build();
    }

    public static PushNotification createMissionCanceledPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(member)
                .name(store.getName())
                .subTitle("포인트가 취소 되었습니다.")
                .checked(false)
                .pushType(PushType.MISSION_CANCELED)
                .id(store.getId())
                .build();
    }

    public static PushNotification createOwnerSuccessPush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(mission.getMissionGroup().getStore().getMember())
                .name(mission.getMember().getName())
                .title("성공요청이 들어왔습니다!")
                .subTitle(" ("+ mission.getMember().getPhone().substring(7) + ")님의 성공여부를 확인 후 수락해주세요.")
                .checked(false)
                .pushType(PushType.OWNER_SUCCESS)
                .subId(mission.getId())
                .build();
    }

    public static PushNotification createOwnerChallengePush(Member member, Store store, Mission mission) {
        return PushNotification.builder()
                .member(mission.getMissionGroup().getStore().getMember())
                .name(mission.getMember().getName())
                .title("고객님이 미션을 도전했습니다!")
                .subTitle("("+ mission.getMember().getPhone().substring(7) + ") 님의 성공여부를 확인 후 수락해주세요.")
                .checked(false)
                .pushType(PushType.OWNER_CHALLENGE)
                .subId(mission.getId())
                .build();
    }

    public void check() {
        this.checked = true;
    }
}
