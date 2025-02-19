package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourImageService;

import java.util.List;

@RestController
@RequestMapping("/api/tour-images")
public class TourImageController {

    private final TourImageService tourImageService;

    @Autowired
    public TourImageController(TourImageService tourImageService) {
        this.tourImageService = tourImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTourImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tourId") String tourId,
            @RequestParam("description") String description,
            @RequestParam("orderIndex") int orderIndex) {

        // Kiểm tra dữ liệu đầu vào
        if (tourId == null || tourId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tour ID không được để trống");
        }
        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Description không được để trống");
        }

        TourImageRequest request = new TourImageRequest();
        request.setTourId(tourId);
        request.setDescription(description);
        request.setOrderIndex(orderIndex);

        return SuccessEntityResponse.OkResponse("Tạo ảnh tour thành công", tourImageService.saveTourImage(file, request));
    }



//    @PutMapping("/{tourImageId}")
//    public ResponseEntity<TourImageResponse> updateTourImage(
//            @PathVariable String tourImageId,
//            @RequestBody @Valid TourImageRequest tourImageRequest) {
//
//        TourImageResponse response = tourImageService.updateTourImage(tourImageId, tourImageRequest);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{tourImageId}")
//    public ResponseEntity<String> deleteTourImage(@PathVariable String tourImageId) {
//        tourImageService.deleteTourImage(tourImageId);
//        return ResponseEntity.ok("Đã xóa ảnh thành công.");
//    }
//
//    @GetMapping("/tour/{tourId}")
//    public ResponseEntity<List<TourImageResponse>> getImagesByTourId(@PathVariable String tourId) {
//        List<TourImageResponse> images = tourImageService.getImagesByTourId(tourId);
//        return ResponseEntity.ok(images);
//    }
}
