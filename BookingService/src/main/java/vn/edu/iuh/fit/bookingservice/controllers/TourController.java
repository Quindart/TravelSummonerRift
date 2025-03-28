package vn.edu.iuh.fit.bookingservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<MessageResponse<TourResponse>> createTour(@RequestParam("price") double price,
                                                              @RequestParam("destination") String destinationJson,
                                                              @RequestParam("image_tour") ArrayList<MultipartFile> images,
                                                              @RequestParam("duration") String duration,
                                                              @RequestParam("description") String description,
                                                              @RequestParam("name") String name
                                                              ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<TourDestinationRequest> destination = objectMapper.readValue(destinationJson, new TypeReference<List<TourDestinationRequest>>() {});
        TourRequest tourRequest = new TourRequest();
        tourRequest.setPrice(price);
        tourRequest.setTourDestinationRequests(destination);
        tourRequest.setDuration(duration);
        tourRequest.setDescription(description);
        tourRequest.setName(name);
        List<TourImageRequest> tourImageRequests = new ArrayList<>();
        for (MultipartFile image : images) {
            TourImageRequest tourImageRequest = new TourImageRequest();
            tourImageRequest.setImage(image);
            tourImageRequests.add(tourImageRequest);
        }
        tourRequest.setTourImageRequests(tourImageRequests);
        TourResponse tourResponse = tourService.createTour(tourRequest);
//        return SuccessEntityResponse.CreateResponse("Tạo tour thành công", tourService.createTour(tourRequest));
        return SuccessEntityResponse.CreateResponse("Tạo tour thành công", tourResponse);
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
    public List<TourResponse> searchTours(
            @RequestParam(required = false) String tourName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String destination) {

        return tourService.searchTours(tourName, category, minPrice, maxPrice, city, destination);
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
}