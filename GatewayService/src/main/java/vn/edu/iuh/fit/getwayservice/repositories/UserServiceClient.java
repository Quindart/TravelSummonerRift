package vn.edu.iuh.fit.getwayservice.repositories;



import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.getwayservice.dtos.MessageResponse;
import vn.edu.iuh.fit.getwayservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.getwayservice.dtos.responses.IntrospectResponse;


public interface UserServiceClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<MessageResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
