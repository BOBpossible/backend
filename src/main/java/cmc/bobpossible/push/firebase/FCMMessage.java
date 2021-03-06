package cmc.bobpossible.push.firebase;

import com.google.firebase.messaging.ApnsConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Apns{
        private Payload payload;

    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Payload{
        private Aps aps;

    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Aps{
        private String sound;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification; // 모든 mobile os를 아우를수 있는 Notification
        private Data data;
        private String token; // 특정 device에 알림을 보내기위해 사용
        private Apns apns;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
        private String title;
        private String body;
    }

}
