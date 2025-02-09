package vn.edu.iuh.fit.bookingservice.exception.errors;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
