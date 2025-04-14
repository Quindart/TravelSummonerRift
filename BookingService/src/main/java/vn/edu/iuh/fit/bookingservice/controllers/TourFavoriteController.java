package vn.edu.iuh.fit.bookingservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.Infra.request.TourFavoriteRequest;
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
        return SuccessEntityResponse.OkResponse("Đã thêm tour vào yêu thích!", null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllFavoritesByUser(@PathVariable String userId) {
        List<TourFavoriteResponse> favorites = tourFavoriteService.getAllFavoritesByUserId(userId);
        return SuccessEntityResponse.OkResponse("Đã tìm thấy danh sách yêu thích!", favorites);
    }
}

