package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.BookingCategoryStatDTO;
import vn.edu.iuh.fit.bookingservice.dtos.TopTourByIdDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.services.StatisticService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/category")
    public MessageResponse<List<BookingCategoryStatDTO>> statisticByCategory(@RequestParam int year) {
        List<BookingCategoryStatDTO> ls = statisticService.countBookingsByCategory(year);
        return MessageResponse.<List<BookingCategoryStatDTO>>builder()
                .success(true)
                .statusCode(200)
                .message("Thống kê booking theo category")
                .data(ls)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("top3-tour")
    public MessageResponse<List<TopTourByIdDTO>> statisticTop3TourByYear(@RequestParam int year) {
        List<TopTourByIdDTO> ls = statisticService.countTop3TourByYear(year);
        return MessageResponse.<List<TopTourByIdDTO>>builder()
                .success(true)
                .statusCode(200)
                .message("Thống kê booking theo category")
                .data(ls)
                .build();
    }


}
