package iuh.fit.notificationservice.exception.errors;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(RuntimeException message) {
        super(message.getMessage());
    }
}
