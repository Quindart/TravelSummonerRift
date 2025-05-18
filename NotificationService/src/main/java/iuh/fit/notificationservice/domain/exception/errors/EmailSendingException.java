package iuh.fit.notificationservice.domain.exception.errors;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super(message);
    }
}
