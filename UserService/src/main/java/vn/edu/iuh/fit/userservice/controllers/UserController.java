package vn.edu.iuh.fit.userservice.controllers;

import java.util.List;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.iuh.fit.userservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public MessageResponse<List<UserResponse>> getUsers() {
        return MessageResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getUsers())
                .message("Danh sách người dùng")
                .build();
    }


}
