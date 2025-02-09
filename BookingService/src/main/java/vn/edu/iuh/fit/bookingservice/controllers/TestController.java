package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.iuh.fit.bookingservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
//import vn.edu.iuh.fit.bookingservice.repositories.httpclient.UserServiceClient;
import vn.edu.iuh.fit.bookingservice.services.TestService;
import vn.edu.iuh.fit.userservice.dtos.TestDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

//    @Autowired
//    private UserServiceClient userServiceClient;


    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("hello")
    public ResponseEntity<MessageResponse<TestDto>> helloword() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", testService.testSerivce());
    }

    @GetMapping("hello1")
    public ResponseEntity<MessageResponse<TestDto>> helloword2() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", new TestDto("Cho Tú"));
    }


//    @PostMapping("/user-info")
//    public ResponseEntity<IntrospectResponse> getUserInfo(@RequestHeader("Authorization") String token) {
//        String accessToken = token.replace("Bearer ", "");
//
//        // Gọi UserService API introspect để kiểm tra token và lấy userId
//        IntrospectRequest request = IntrospectRequest.builder().token(accessToken).build();
//
//        ResponseEntity<MessageResponse<IntrospectResponse>> response = userServiceClient.introspect(request);
//
//
////        if (response.getStatusCode() == HttpStatus.OK) {
////            return ResponseEntity.ok(response.getBody().getData());
////        } else {
////            return ResponseEntity.status(response.getStatusCode()).body(response.getBody().getData());
////        }
//        return ResponseEntity.ok(response.getBody().getData());
//
//    }

//    @PostMapping("/introspect")
//    public ResponseEntity<?> testIntrospect(@RequestBody IntrospectRequest request) {
//        try {
//            MessageResponse<IntrospectResponse> response = userServiceClient.introspect(request);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Lỗi khi gọi introspect: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/test-users")
//    public ResponseEntity<MessageResponse<List<UserResponse>>> testGetUsers() {
//        MessageResponse<List<UserResponse>> response = userServiceClient.getAllUsers();
//        List<UserResponse> users = response.getData();
//        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", users);
//
//    }
}
