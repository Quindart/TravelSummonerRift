package vn.edu.iuh.fit.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.iuh.fit.bookingservice.exception.errors.*;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> NotFoundException(NotFoundException exc){
        MessageResponse error = new MessageResponse(HttpStatus.NOT_FOUND.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageDto> BadrequestException(BadRequestException exc){
        MessageResponse error = new MessageResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ErrorMessageDto> BadGatewayException(BadGatewayException exc){
        MessageResponse error = new MessageResponse(HttpStatus.BAD_GATEWAY.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ConflicException.class)
    public ResponseEntity<ErrorMessageDto> ConflicException(ConflicException exc){
        MessageResponse error = new MessageResponse(HttpStatus.CONFLICT.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageDto> UnauthorizedException(UnauthorizedException exc){
        MessageResponse error = new MessageResponse(HttpStatus.UNAUTHORIZED.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorMessageDto> InternalServerError(InternalServerErrorException exc){
        MessageResponse error = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageDto> RuntimeErrorException(RuntimeException exc){
        MessageResponse error = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exc.getMessage(),false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return  new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
