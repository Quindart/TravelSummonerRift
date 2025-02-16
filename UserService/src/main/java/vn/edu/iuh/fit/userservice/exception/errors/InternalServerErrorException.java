package vn.edu.iuh.fit.userservice.exception.errors;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(RuntimeException message) {
        super(message.getMessage());
    }
}
