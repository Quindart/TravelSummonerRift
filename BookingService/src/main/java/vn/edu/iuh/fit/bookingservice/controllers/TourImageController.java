package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.repositories.TourImageRepository;
import vn.edu.iuh.fit.bookingservice.services.CloudinaryService;
import vn.edu.iuh.fit.bookingservice.services.TourImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tour-images")
public class TourImageController {

    private final TourImageService tourImageService;

    @Autowired
    public TourImageController(TourImageService tourImageService, TourImageRepository tourImageRepository, CloudinaryService cloudinaryService) {
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
        request.setImage(file);

        return SuccessEntityResponse.OkResponse("Tạo ảnh tour thành công", tourImageService.saveTourImage(request));
    }

    @PutMapping("/update/{tourImageId}")
    public ResponseEntity<?> updateTourImage(
            @PathVariable("tourImageId") String tourImageId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("orderIndex") int orderIndex,
            @RequestParam("isActive") boolean isActive
    ) {

        // Kiểm tra dữ liệu đầu vào
        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Description không được để trống");
        }

        try {
            // Tạo request DTO để truyền vào service
            TourImageRequest request = new TourImageRequest();
            request.setDescription(description);
            request.setOrderIndex(orderIndex);

            return SuccessEntityResponse.OkResponse("Cập nhật ảnh tour thành công", tourImageService.updateTourImage(tourImageId, request, file));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi cập nhật ảnh tour: " + e.getMessage());
        }
    }




    @DeleteMapping("/delete/{tourImageId}")
    public ResponseEntity<String> deleteTourImage(@PathVariable String tourImageId) {
        try {
            tourImageService.deleteTourImage(tourImageId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Đã xóa ảnh thành công.");
    }

}
