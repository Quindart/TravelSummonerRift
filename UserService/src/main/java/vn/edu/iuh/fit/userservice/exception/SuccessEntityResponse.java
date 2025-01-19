package vn.edu.iuh.fit.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.edu.iuh.fit.userservice.dtos.TestDto;

public  class SuccessEntityResponse {
    public static  <T>  ResponseEntity<MessageResponse<T>> FoundResponse(String message, T data){
            MessageResponse msg = new MessageResponse(HttpStatus.FOUND.value(),message,true,data);
            return  ResponseEntity.status(HttpStatus.FOUND).body(msg);
    }

    public static  <T>  ResponseEntity<MessageResponse<T>> CreateResponse(String message, T data){
        MessageResponse msg = new MessageResponse(HttpStatus.CREATED.value(),message,true,data);
        return  ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    public static  <T>  ResponseEntity<MessageResponse<T>> OkResponse(String message, T data){
        MessageResponse msg = new MessageResponse(HttpStatus.OK.value(),message,true,data);
        return  ResponseEntity.status(HttpStatus.OK).body(msg);
    }
}
