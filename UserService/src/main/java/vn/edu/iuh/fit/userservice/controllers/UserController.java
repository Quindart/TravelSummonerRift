package vn.edu.iuh.fit.userservice.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.userservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.UserRegisterRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.UserUpdateRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.entities.User;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.userservice.infra.booking.dto.BookingOfUserResponse;
import vn.edu.iuh.fit.userservice.infra.booking.service.BookingOfUserService;
import vn.edu.iuh.fit.userservice.repositories.UserRepository;
import vn.edu.iuh.fit.userservice.services.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingOfUserService bookingOfUserService;
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/my-info")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public MessageResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(userId, request);
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Cập nhật thông tin người dùng thành công.")
                .data(updatedUser)
                .build();
    }

    @PutMapping("/me")
    public MessageResponse<UserResponse> updateMe(
            @RequestBody UserUpdateRequest request) {

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));

        UserResponse updatedUser = userService.updateUser(user.getUserId(), request);
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Cập nhật thông tin người dùng thành công.")
                .data(updatedUser)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse<UserResponse> updateAvatar(
            @PathVariable String userId,
            @RequestPart("avatar") MultipartFile avatar) throws IOException {

        UserResponse updatedUser = userService.updateUserAvatar(userId, avatar);
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Cập nhật ảnh đại diện thành công.")
                .data(updatedUser)
                .build();
    }

    @PutMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse<UserResponse> updateMeAvatar(

            @RequestPart("avatar") MultipartFile avatar) throws IOException {

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));

        UserResponse updatedUser = userService.updateUserAvatar(user.getUserId(), avatar);
        return MessageResponse.<UserResponse>builder()
                .statusCode(200)
                .success(true)
                .message("Cập nhật ảnh đại diện thành công.")
                .data(updatedUser)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public MessageResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return MessageResponse.<Void>builder()
                .statusCode(200)
                .success(true)
                .message("Xóa tài khoản thành công.")
                .build();
    }






}
