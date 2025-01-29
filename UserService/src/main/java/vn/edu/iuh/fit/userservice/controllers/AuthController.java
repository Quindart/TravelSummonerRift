package vn.edu.iuh.fit.userservice.controllers;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.userservice.dtos.requests.AuthenticationRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.AuthenticationResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.services.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<MessageResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return SuccessEntityResponse.OkResponse("Đăng nhập thành công", result);
    }

    @PostMapping("/introspect")
    ResponseEntity<MessageResponse<IntrospectResponse>> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return SuccessEntityResponse.OkResponse("Kiểm tra token thành công", result);
    }

    
}
