package vn.edu.iuh.fit.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.userservice.dtos.TestDto;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;

@RestController
@RequestMapping("/api")
public class TestController  {


    @GetMapping("hello")
    public ResponseEntity<MessageResponse<TestDto>> helloword(){
       try {
           int i = 0;

           if(i == 1) throw new NotFoundException("Chos Tu Biến Thái");
           return SuccessEntityResponse.FoundResponse("Đã tìm thấy", new TestDto("Cho Tú"));
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }
}
