package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.stereotype.Service;

import vn.edu.iuh.fit.userservice.dtos.TestDto;

@Service
public class TestService {
    public TestDto testSerivce() {
        int p = 1;
        if (p == 1) throw new RuntimeException("1h30 rồi dmm");
        return new TestDto("Chóa Tú");
    }
}
