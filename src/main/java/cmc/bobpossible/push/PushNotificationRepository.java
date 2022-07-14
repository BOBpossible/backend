package cmc.bobpossible.push;

import cmc.bobpossible.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
    List<PushNotification> findByMember(Member member);

    List<PushNotification> findByMemberOrderByIdDesc(Member member);
}
