package vn.edu.iuh.fit.userservice.exception.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
