package iuh.fit.notificationservice.domain.exception.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String thisAccountAlreadyExisted){
        super();
    }
}
