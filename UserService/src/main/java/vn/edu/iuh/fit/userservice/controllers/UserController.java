package vn.edu.iuh.fit.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.userservice.dtos.TestDto;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<MessageResponse<List<UserResponse>>> getUsers(){
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", userService.getUsers());
    }
}
