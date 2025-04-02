package vn.edu.iuh.fit.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessEntityResponse {

    public static <T> ResponseEntity<MessageResponse<T>> FoundResponse(String message, T data) {
        MessageResponse msg = new MessageResponse(HttpStatus.OK.value(), message, true, data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    public static <T> ResponseEntity<MessageResponse<T>> CreateResponse(String message, T data) {
        MessageResponse msg = new MessageResponse(HttpStatus.OK.value(), message, true, data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    public static <T> ResponseEntity<MessageResponse<T>> OkResponse(String message, T data) {
        MessageResponse msg = new MessageResponse(HttpStatus.OK.value(), message, true, data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }
}
