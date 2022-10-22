package cmc.bobpossible.push.firebase;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.push.PushNotification;
import cmc.bobpossible.push.PushNotificationRepository;
import cmc.bobpossible.store.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private String API_URL = "https://fcm.googleapis.com/v1/projects/bobpossible-67a56/messages:send";
    private final ObjectMapper objectMapper;
    private final PushNotificationRepository pushNotificationRepository;
    private final OkHttpClient client;

    @Transactional
    public void sendReviewPush(String targetToken ,Member member, Store store, Mission mission) throws IOException {
        sendMessageTo(targetToken, "reviewPush", store.getName()+"의 음식 맛있었다면 리뷰를 남겨주세요.", "reviewPush", store.getName()+"의 음식 맛있었다면 리뷰를 남겨주세요.");

    }

    public void sendMessageTo(String targetToken, String title, String body, String dTitle, String dBody) throws IOException {
        String message = makeMessage(targetToken, title, body, dTitle, dBody);

//        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

    // 파라미터를 FCM이 요구하는 body 형태로 만들어준다.
    private String makeMessage(String targetToken, String title, String body, String dTitle, String dBody) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .data(FCMMessage.Data.builder()
                                .title(dTitle)
                                .body(dBody)
                                .build()
                        )
                        .apns(FCMMessage.Apns.builder()
                                        .payload(
                                                FCMMessage.Payload.builder()
                                                        .aps(FCMMessage.Aps.builder().sound("default").build())
                                                        .build())
                                        .build())
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/bobpossible-67a56-firebase-adminsdk-vvgfz-4eb266607b.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}


