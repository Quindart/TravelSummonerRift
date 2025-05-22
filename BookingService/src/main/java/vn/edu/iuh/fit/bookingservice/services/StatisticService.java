package vn.edu.iuh.fit.bookingservice.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.BookingCategoryStatDTO;
import vn.edu.iuh.fit.bookingservice.dtos.MonthlyRevenueDTO;
import vn.edu.iuh.fit.bookingservice.dtos.TopTourByIdDTO;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourRepository tourRepository;

    public List<BookingCategoryStatDTO> countBookingsByCategory(int year) {
        return bookingRepository.countBookingsByCategoryInYear(year);
    }

    public List<TopTourByIdDTO> countTop3TourByYear(int year) {
        return tourRepository.findTop3TourIdsByYear(year);
    }

    public List<MonthlyRevenueDTO> statisticMonthlyRevenueByYear(int year) {
        return bookingRepository.getMonthlyRevenueByYear(year);
    }
}
