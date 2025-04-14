package vn.edu.iuh.fit.bookingservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.Infra.request.TourFavoriteRequest;
import vn.edu.iuh.fit.bookingservice.services.TourFavoriteService;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class TourFavoriteController {

    private final TourFavoriteService tourFavoriteService;

    @PostMapping("")
    public ResponseEntity<?> addToFavorite(@RequestBody TourFavoriteRequest request) {
        tourFavoriteService.addTourToFavorite(request.getUserId(), request.getTourId());
        return ResponseEntity.ok("Đã thêm tour vào yêu thích!");
    }
}

