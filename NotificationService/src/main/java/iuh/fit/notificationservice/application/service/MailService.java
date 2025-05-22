package iuh.fit.notificationservice.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailReplyTo;
import sibModel.SendSmtpEmailTo;

import java.util.List;
import java.util.Map;

@Service
public class MailService {
    @Value("${brevo.apikey}")
    private String apiKeyString;
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    public boolean sendUserConfirmation(String toEmail, String otp) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(apiKeyString);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        sendSmtpEmail.setSubject("OTP Verification");
        sendSmtpEmail.setTemplateId(3L);
        sendSmtpEmail.setTo(List.of(new SendSmtpEmailTo().email(toEmail).name(otp)));
        sendSmtpEmail.setReplyTo(new SendSmtpEmailReplyTo().email("support@yourdomain.com").name("Support"));
        sendSmtpEmail.setParams(Map.of("otp", otp, "url", "https://yourdomain.com/verify?code=" + otp));

        try {
            CreateSmtpEmail response = apiInstance.sendTransacEmail(sendSmtpEmail);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
