package vn.edu.iuh.fit.bookingservice.exception.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
