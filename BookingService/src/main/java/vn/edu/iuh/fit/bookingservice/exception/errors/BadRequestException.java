package vn.edu.iuh.fit.bookingservice.exception.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
