package vn.edu.iuh.fit.bookingservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.Infra.request.TourFavoriteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class TourHistoryController {
    private final TourHistoryService tourHistoryService;

    @PostMapping("")
    public ResponseEntity<?> addToHistory(@RequestBody TourFavoriteRequest request) {
        tourHistoryService.addTourToHistory(request.getUserId(), request.getTourId());
        List<TourHistoryResponse> history = tourHistoryService.getUserTourHistory(request.getUserId());
        return SuccessEntityResponse.OkResponse("Đã cập nhật lịch sử xem tour!", history);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserHistory(@PathVariable String userId) {
        List<TourHistoryResponse> history = tourHistoryService.getUserTourHistory(userId);
        return SuccessEntityResponse.OkResponse("Đã tìm thấy lịch sử xem!", history);
    }
}
