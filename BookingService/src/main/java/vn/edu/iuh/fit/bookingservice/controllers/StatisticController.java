package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.BookingCategoryStatDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.services.StatisticService;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public MessageResponse<List<BookingCategoryStatDTO>> statisticByCategory() {
        List<BookingCategoryStatDTO> ls = statisticService.countBookingsByCategory();
        return MessageResponse.<List<BookingCategoryStatDTO>>builder()
                .success(true)
                .statusCode(200)
                .message("Thống kê booking theo category")
                .data(ls)
                .build();
    }
}
