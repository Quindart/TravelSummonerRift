package vn.edu.iuh.fit.bookingservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.TourScheduleDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/tours")
public class TourController {

    private static final Logger log = LoggerFactory.getLogger(TourController.class);

    @Autowired
    TourService tourService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<TourResponse>>> getAllTours() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách tour", tourService.getAllTours());
    }

    @GetMapping("/{tourId}")
    public ResponseEntity<MessageResponse<TourResponse>> getTourById(@PathVariable String tourId) {

        return SuccessEntityResponse.FoundResponse("Đã tìm thấy tour", tourService.getTourById(tourId));
    }

    @PostMapping
    public MessageResponse<TourResponse> createTour(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.createTour(tourRequest);
        return MessageResponse.<TourResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Tạo tour thành công!")
                .data(tourResponse)
                .build();
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

    @GetMapping("/search")
    public MessageResponse<List<TourResponse>> searchTours(
            @RequestParam(required = false) String tourName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String destination) {

        var data =  tourService.searchTours(tourName, category, minPrice, maxPrice, city, destination);
        return MessageResponse.<List<TourResponse>>builder()
                .success(true)
                .statusCode(200)
                .data(data)
                .build();
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<MessageResponse<List<TourResponse>>> searchToursByKeyword(@PathVariable String keyword) {
        try {
            keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Error decoding keyword: {}", e.getMessage());
        }
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách tour", tourService.searchToursByKeyword(keyword));
    }

    @GetMapping("/tour-overviews")
    public ResponseEntity<MessageResponse<List<TourOverviewResponse>>> getAllTourOverviews() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách tour", tourService.getAllTourOverviews());
    }

    @GetMapping("/{tourId}/{tourScheduleId}/tourSchedule-detail")
    public ResponseEntity<MessageResponse<TourScheduleDetailResponse>> getTourScheduleDetail(
            @PathVariable String tourId, @PathVariable String tourScheduleId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy chi tiết lịch trình tour",
                tourService.getTourScheduleDetail(tourId, tourScheduleId));
    }

    @GetMapping("/{tourId}/tour-images")
    public ResponseEntity<MessageResponse<List<TourImageResponse>>> getTourImages(@PathVariable String tourId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách hình ảnh tour", tourService.getTourImages(tourId));
    }

    @GetMapping("/{tourId}/tour-notes")
    public ResponseEntity<MessageResponse<List<TourNoteResponse>>> getTourNotes(@PathVariable String tourId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách ghi chú tour", tourService.getTourNotes(tourId));
    }


    @GetMapping("/{categoryId}/tours")
    public MessageResponse<List<TourOverviewResponse>> getTours(@PathVariable String categoryId) {
        return MessageResponse.<List<TourOverviewResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Danh sách tour theo category")
                .data(tourService.getToursByCategory(categoryId))
                .build();
    }

    @GetMapping("/{tourId}/tour-schedule")
    public MessageResponse<List<TourScheduleResponse>> getTourScheduleByTourId(@PathVariable String tourId) {
        return MessageResponse.<List<TourScheduleResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Danh sách tour-schedule theo tourId")
                .data(tourService.getTourSchedules(tourId))
                .build();
    }
}