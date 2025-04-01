package vn.edu.iuh.fit.userservice.controllers;

import java.util.List;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.iuh.fit.userservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.UserRegisterRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.infra.booking.dto.BookingOfUserResponse;
import vn.edu.iuh.fit.userservice.infra.booking.service.BookingOfUserService;
import vn.edu.iuh.fit.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingOfUserService bookingOfUserService;


    @GetMapping()
    public MessageResponse<List<UserResponse>> getUsers() {
        return MessageResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getUsers())
                .message("Danh sách người dùng")
                .build();
    }

    @GetMapping("demo")
    public String demo() {
        return "hihi";
    }

    @GetMapping("demo-book-of-user")
    public ResponseEntity<MessageResponse<BookingOfUserResponse>> getBookingOfUser() {
        BookingOfUserResponse bookingOfUserResponse = bookingOfUserService.getBookingOfUser("8f68b683-d5ef-4ede-88fc-37e6e56ef9b1");
        return SuccessEntityResponse.FoundResponse("Get bookings of user success", bookingOfUserResponse);
    }

    @PostMapping("/register")
    public MessageResponse<UserResponse> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Đăng ký người dùng thành công")
                .data(userService.registerUser(request))
                .build();
    }

    @GetMapping("my-info")
    public MessageResponse<UserResponse> getMyInfo() {
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Thông tin người dùng")
                .data(userService.getMyInfo())
                .build();
    }

    @GetMapping("/{id}")
    public MessageResponse<UserResponse> getUserById(@PathVariable String id) {
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Thông tin người dùng")
                .data(userService.getUserById(id))
                .build();
    }

}
