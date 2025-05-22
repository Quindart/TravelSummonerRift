package iuh.fit.notificationservice.application.service;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import iuh.fit.notificationservice.application.dto.request.notification.NotificationRequest;
import iuh.fit.notificationservice.domain.exception.errors.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class FirebaseService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    public String sendNotificationFirebase(NotificationRequest notificationRequest){
        Notification notification = Notification.builder()
                .setTitle(notificationRequest.getTitle())
                .setBody(notificationRequest.getBody())
                .setImage(notificationRequest.getImage())
                .build();
        Message message = Message.builder()
                .setToken(notificationRequest.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationRequest.getData())
                .build();
        try {
            firebaseMessaging.send(message);
            return "Send notification success";
        } catch (FirebaseException e) {
           e.printStackTrace();
           throw new InternalServerErrorException("firebase error");
        }
    }
}
