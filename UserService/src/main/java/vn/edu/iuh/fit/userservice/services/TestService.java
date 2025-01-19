package vn.edu.iuh.fit.userservice.services;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.dtos.TestDto;
import vn.edu.iuh.fit.userservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;

@Service
public class TestService {
    public TestDto testSerivce(){
                int p = 1;
                if(p==1) throw new RuntimeException("1h30 rồi dmm");
            return new TestDto("Chóa Tú");
    }
}
