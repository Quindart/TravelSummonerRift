package vn.edu.iuh.fit.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.userservice.dtos.TestDto;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.userservice.services.TestService;

@RestController
@RequestMapping("/api")
public class TestController  {
    private  TestService testService;
    public  TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping("hello")
    public ResponseEntity<MessageResponse<TestDto>> helloword(){
           return SuccessEntityResponse.FoundResponse("Đã tìm thấy", testService.testSerivce());
    }
    @GetMapping("hello1")
    public ResponseEntity<MessageResponse<TestDto>> helloword2(){
            return SuccessEntityResponse.FoundResponse("Đã tìm thấy", new TestDto("Cho Tú"));
    }
}
