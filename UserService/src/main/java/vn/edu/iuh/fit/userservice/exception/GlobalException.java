package vn.edu.iuh.fit.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import vn.edu.iuh.fit.userservice.exception.errors.*;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> NotFoundException(NotFoundException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageDto> BadrequestException(BadRequestException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ErrorMessageDto> BadGatewayException(BadGatewayException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.BAD_GATEWAY.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ConflicException.class)
    public ResponseEntity<ErrorMessageDto> ConflicException(ConflicException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.CONFLICT.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageDto> UnauthorizedException(UnauthorizedException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.UNAUTHORIZED.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorMessageDto> InternalServerError(InternalServerErrorException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageDto> RuntimeErrorException(RuntimeException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new MessageResponse(413, "Kích thước file vượt quá giới hạn cho phép", false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorMessageDto errorDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), enumKey, false);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDto> handleAccessDeniedException(AccessDeniedException exc) {
        MessageResponse error = new MessageResponse(HttpStatus.FORBIDDEN.value(), "Bạn không có quyền truy cập!", false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleNoResourceFoundException(NoResourceFoundException ex) {

        MessageResponse error = new MessageResponse(HttpStatus.NOT_FOUND.value(), "Không tìm thấy tài nguyên: " + ex.getMessage(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        MessageResponse error = new MessageResponse(HttpStatus.NOT_FOUND.value(), "Không tìm thấy URL: " + ex.getRequestURL(), false);
        ErrorMessageDto errorDto = new ErrorMessageDto(error.getStatusCode(), error.getMessage(), error.isSuccess());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessageDto> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Phương thức HTTP không được hỗ trợ cho đường dẫn này: " + ex.getMethod(),
                false
        );
        return new ResponseEntity<>(errorMessageDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
