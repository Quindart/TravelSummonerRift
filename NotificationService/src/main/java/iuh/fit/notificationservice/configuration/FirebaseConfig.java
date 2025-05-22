package iuh.fit.notificationservice.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {



    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("tour-70dad-firebase-adminsdk-fbsvc-ef4d3e10c9.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "Tour");
        return FirebaseMessaging.getInstance(app);
    }
}
