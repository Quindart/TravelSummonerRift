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
import vn.edu.iuh.fit.userservice.services.TestService;
import vn.edu.iuh.fit.userservice.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    private TestService testService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/test-openfeign")
    public ResponseEntity<List<UserResponse>> testOpenFeign() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // add để được commit ==Zy

    @GetMapping("/em-zy-cute-voai")
    public String testCICD() {
        return "Em Zy làm xong CICD rồi đó mn, djtmemay che nua di  ấdđ"  ;
    }
}
