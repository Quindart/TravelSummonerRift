package vn.edu.iuh.fit.getwayservice.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.getwayservice.dtos.MessageResponse;
import vn.edu.iuh.fit.getwayservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.getwayservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.getwayservice.repositories.UserServiceClient;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserServiceClient userServiceClient;

    public Mono<MessageResponse<IntrospectResponse>> introspect(String token) {
        return userServiceClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}