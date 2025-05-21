package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourDestinationService;

import java.util.List;

@RestController
@RequestMapping("/tour-destinations")
public class TourDestinationController {

    @Autowired
    TourDestinationService tourDestinationService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<TourDestinationResponse>>> getAllTourDestinations() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách điểm đến", tourDestinationService.getAllTourDestination());
    }
    @GetMapping("/{tourDestinationId}")
    public ResponseEntity<MessageResponse<TourDestinationResponse>> getTourDestinationById(@PathVariable String tourDestinationId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy điểm đến", tourDestinationService.getTourDestinationById(tourDestinationId));
    }

    @GetMapping("tourId/{tourId}")
    public ResponseEntity<MessageResponse<List<TourDestinationResponse>>> getTourDestinationByTourId(@PathVariable String tourId) {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy điểm đến", tourDestinationService.getAllTourDestinationByTourId(tourId));
    }


    @PostMapping
    public ResponseEntity<MessageResponse<TourDestinationResponse>> createTourDestination(@RequestBody @Valid TourDestinationRequest tourDestinationRequest) {
        return SuccessEntityResponse.CreateResponse("Tạo điểm đến thành công", tourDestinationService.createTourDestination(tourDestinationRequest));
    }

    @PutMapping("/{tourDestinationId}")
    public ResponseEntity<MessageResponse<TourDestinationResponse>> updateTourDestination(
            @PathVariable String tourDestinationId, @RequestBody @Valid TourDestinationRequest tourDestinationRequest) {
        return SuccessEntityResponse.OkResponse("Cập nhật điểm đến thành công", tourDestinationService.updateTourDestination(tourDestinationId, tourDestinationRequest));
    }

    @DeleteMapping("/{tourDestinationId}")
    public ResponseEntity<MessageResponse<Void>> deleteTourDestination(@PathVariable String tourDestinationId) {
        tourDestinationService.deleteTourDestination(tourDestinationId);
        return SuccessEntityResponse.OkResponse("Xóa điểm đến thành công", null);
    }
}