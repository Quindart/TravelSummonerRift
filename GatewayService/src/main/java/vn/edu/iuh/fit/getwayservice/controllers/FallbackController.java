package vn.edu.iuh.fit.getwayservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.getwayservice.dtos.MessageResponse;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user")
    public Mono<ResponseEntity<MessageResponse<String>>> userFallback() {
        MessageResponse<String> response = MessageResponse.<String>builder()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("User service hiện đang không phản hồi. Vui lòng thử lại sau.")
                .success(false)
                .data(null)
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @PostMapping("/user")
    public Mono<ResponseEntity<MessageResponse<String>>> userFallbackPost() {
        MessageResponse<String> response = MessageResponse.<String>builder()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("User service hiện đang không phản hồi. Vui lòng thử lại sau.")
                .success(false)
                .data(null)
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/booking")
    public Mono<ResponseEntity<MessageResponse<String>>> bookingFallback() {
        MessageResponse<String> response = MessageResponse.<String>builder()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Booking service hiện đang không phản hồi. Vui lòng thử lại sau.")
                .success(false)
                .data(null)
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @PostMapping("/booking")
    public Mono<ResponseEntity<MessageResponse<String>>> bookingFallbackPost() {
        MessageResponse<String> response = MessageResponse.<String>builder()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Booking service hiện đang không phản hồi. Vui lòng thử lại sau.")
                .success(false)
                .data(null)
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }
}


