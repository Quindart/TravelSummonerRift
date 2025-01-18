package vn.edu.iuh.fit.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.userservice.dtos.TestDto;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;

@RestController
@RequestMapping("/api")
public class TestController  {


    @GetMapping("hello")
    public ResponseEntity<MessageResponse<TestDto>> helloword(){
       try {
           int i = 1;
//           if(i == 1) throw  throwNotFoundException("Chos Tu Biến Thái");
           if(i == 1) throw new NotFoundException("Chos Tu Biến Thái");
           MessageResponse<TestDto > message =  new MessageResponse<>(200,"hello123o",true,new TestDto("Chos Tu"));
           return ResponseEntity.status(200).body(message);
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       } catch (Throwable e) {
           throw new RuntimeException(e);
       }
    }
}
