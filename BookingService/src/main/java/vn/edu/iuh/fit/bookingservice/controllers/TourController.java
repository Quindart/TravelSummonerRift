package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {

    @Autowired
    TourService tourService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<TourResponse>>> getAllTours() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách tour", tourService.getAllTours());
    }

    @GetMapping("/{tourId}")
    public ResponseEntity<MessageResponse<TourResponse>> getTourById(@PathVariable String tourId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy tour", tourService.getTourById(tourId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<TourResponse>> createTour(@RequestBody @Valid TourRequest tourRequest) {
        return SuccessEntityResponse.CreateResponse("Tạo tour thành công", tourService.createTour(tourRequest));
    }

    @PutMapping("/{tourId}")
    public ResponseEntity<MessageResponse<TourResponse>> updateTour(
            @PathVariable String tourId, @RequestBody @Valid TourRequest tourRequest) {
        return SuccessEntityResponse.OkResponse("Cập nhật tour thành công", tourService.updateTour(tourId, tourRequest));
    }

    @DeleteMapping("/{tourId}")
    public ResponseEntity<MessageResponse<Void>> deleteTour(@PathVariable String tourId) {
        tourService.deleteTour(tourId);
        return SuccessEntityResponse.OkResponse("Xóa tour thành công", null);
    }
}