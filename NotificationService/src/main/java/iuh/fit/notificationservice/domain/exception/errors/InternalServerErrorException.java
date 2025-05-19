package iuh.fit.notificationservice.domain.exception.errors;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(RuntimeException message) {
        super(message.getMessage());
    }
}
