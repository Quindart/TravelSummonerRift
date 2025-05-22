package vn.edu.iuh.fit.getwayservice.configs;
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.getwayservice.dtos.MessageResponse;
import vn.edu.iuh.fit.getwayservice.services.UserService;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    UserService userService;
    ObjectMapper objectMapper;

//    CircuitBreakerRegistry circuitBreakerRegistry;

    @NonFinal
    private String[] publicEndpoints = {
            //public endpoint api
            "/user-service/api/.*",
            "/user-service/auth/.*",
            "/user-service/users/register",
            "/booking-service/tours.*",
            "/booking-service/destination.*",
            "/booking-service/tour-destination.*",
            "/booking-service/reviews.*",
            "/booking-service/category-tours.*",
            "/booking-service/vnpay.*",
            "/notification-service/.*",
            "/chatbot-service/.*",
            //swagger
            "/swagger-ui.*",
            "/swagger-ui/.*",
            "/swagger-resources/.*",
            "/v3/api-docs/.*",
            "/webjars/.*",
            ".*/v3/api-docs",
            ".*/swagger-resources.*",
            ".*/docs"


    };

    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefix;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("Enter authentication filter....");

//        CircuitBreaker userServiceCircuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");


//        System.out.println("CB state: "  + userServiceCircuitBreaker.getState());
//        System.out.println("Failure Rate:" + userServiceCircuitBreaker.getMetrics().getFailureRate());
//        System.out.println("Current Number of Failed Calls" +  userServiceCircuitBreaker.getMetrics().getNumberOfFailedCalls());

        if(isPublicEndpoint(exchange.getRequest()))
        {
            return chain.filter(exchange);
        }

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse());

        String token = authHeader.get(0).replace("Bearer ", "");
        log.info("Token: {}", token);

        return userService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getData().isValid())
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse());
        }).onErrorResume(throwable -> {
            System.out.println("Error: " + throwable.getMessage());
            return unauthenticated(exchange.getResponse());
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints).anyMatch(s->request.getURI().getPath().matches(apiPrefix + s));
    }

    Mono<Void> unauthenticated(ServerHttpResponse response){
        MessageResponse<?> apiResponse = MessageResponse.builder()
                .statusCode(401)
                .success(false)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
        response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Content-Type");
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}