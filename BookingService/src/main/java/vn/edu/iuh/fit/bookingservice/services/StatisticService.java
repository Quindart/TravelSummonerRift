package vn.edu.iuh.fit.bookingservice.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.BookingCategoryStatDTO;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<BookingCategoryStatDTO> countBookingsByCategory() {
        return bookingRepository.countBookingsByCategoryNative();
    }
}
