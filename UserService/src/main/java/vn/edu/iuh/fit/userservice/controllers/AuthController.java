package vn.edu.iuh.fit.userservice.controllers;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.userservice.dtos.requests.*;
import vn.edu.iuh.fit.userservice.dtos.responses.AuthenticationResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.services.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/logout")
    ResponseEntity<MessageResponse<Void>> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return SuccessEntityResponse.OkResponse("Đăng xuất thành công", null);
    }

    @PostMapping("/refresh")
    ResponseEntity<MessageResponse<AuthenticationResponse>> refresh(@RequestBody @Valid RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return SuccessEntityResponse.OkResponse("Làm mới token thành công", result);
    }

    @PostMapping("/forgot-password")
    public MessageResponse<Void> sendOtp(@RequestParam String email) {
        authenticationService.processForgotPassword(email);
        return MessageResponse.<Void>builder()
                .statusCode(200)
                .success(true)
                .message("Đã gửi OTP về email của bạn, xin vui lòng kiểm tra.")
                .build();
    }

    @PostMapping("/reset-password")
    public MessageResponse<Void> verifyOtp(@RequestBody ResetPasswordRequest otpRequest) {
        authenticationService.resetPassword(otpRequest);
        return MessageResponse.<Void>builder()
                .statusCode(200)
                .success(true)
                .message("Đặt lại mật khẩu thành công.")
                .build();
    }


}
