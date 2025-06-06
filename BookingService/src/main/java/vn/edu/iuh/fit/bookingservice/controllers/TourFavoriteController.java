package vn.edu.iuh.fit.bookingservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourFavoriteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourFavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class TourFavoriteController {

    private final TourFavoriteService tourFavoriteService;

    @PostMapping("")
    public ResponseEntity<?> addToFavorite(@RequestBody TourFavoriteRequest request) {
        tourFavoriteService.addTourToFavorite(request.getUserId(), request.getTourId());
        List<TourFavoriteResponse> favorites = tourFavoriteService.getAllFavoritesByUserId(request.getUserId());
        return SuccessEntityResponse.OkResponse("Đã thêm tour vào yêu thích!", favorites);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllFavoritesByUser(@PathVariable String userId) {
        List<TourFavoriteResponse> favorites = tourFavoriteService.getAllFavoritesByUserId(userId);
        return SuccessEntityResponse.OkResponse("Đã tìm thấy danh sách yêu thích!", favorites);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateFavorites(
            @PathVariable String userId,
            @RequestBody List<String> tourIds) {
        tourFavoriteService.updateTourFavorite(userId, tourIds);
        return SuccessEntityResponse.OkResponse("Danh sách yêu thích đã được cập nhật!", null);
    }

    @DeleteMapping("/{userId}/{tourId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable String userId, @PathVariable String tourId) {
        tourFavoriteService.deleteTourFavorite(userId, tourId);
        List<TourFavoriteResponse> favorites = tourFavoriteService.getAllFavoritesByUserId(userId);
        return SuccessEntityResponse.OkResponse("Đã xóa tour khỏi danh sách yêu thích!", favorites);
    }
}

