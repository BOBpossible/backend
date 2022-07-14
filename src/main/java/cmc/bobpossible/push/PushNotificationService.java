package cmc.bobpossible.push;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.push.dto.GetPushRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;
import static cmc.bobpossible.config.BaseResponseStatus.INVALID_PUSH_NOTIFICATION_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PushNotificationService {

    private final MemberRepository memberRepository;
    private final PushNotificationRepository pushNotificationRepository;

    public List<GetPushRes> getPushNotifications() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<PushNotification> push = pushNotificationRepository.findByMember(member);

        return push.stream().map(GetPushRes::new).collect(Collectors.toList());
    }

    @Transactional
    public void checkPush(Long pushNotificationId) throws BaseException {

        PushNotification pushNotification = pushNotificationRepository.findById(pushNotificationId)
                .orElseThrow(() -> new BaseException(INVALID_PUSH_NOTIFICATION_ID));

        pushNotification.check();
    }
}
