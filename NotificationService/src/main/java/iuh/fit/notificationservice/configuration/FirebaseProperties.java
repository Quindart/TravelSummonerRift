package iuh.fit.notificationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration()
public class FirebaseProperties {
    private String googleCredentials;

    public String getGoogleCredentials() {
        return googleCredentials;
    }

    public void setGoogleCredentials(String googleCredentials) {
        this.googleCredentials = googleCredentials;
    }
}
