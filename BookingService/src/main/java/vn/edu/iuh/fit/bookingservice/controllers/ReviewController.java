package vn.edu.iuh.fit.bookingservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.enums.SortFieldReview;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;
import vn.edu.iuh.fit.bookingservice.services.impl.ReviewServiceImpl;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewService;

    @GetMapping("{tour_id}")
    public MessageResponse<List<ReviewResponse>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "review_date") SortFieldReview sortby,
            @RequestParam(defaultValue = "DESC") String direction,
            @PathVariable("tour_id") String tour_id
    ) {
        return MessageResponse.<List<ReviewResponse>>builder()
                .statusCode(200)
                .success(true)
                .data(reviewService.getReviewByTourId(page,size,sortby.toString(),direction,tour_id))
                .message("Lấy danh sách thành công")
                .build();
    }

    @GetMapping("/total-rating/{tourId}")
    private ResponseEntity<MessageResponse<List<RatingTotalResponse>>> getRatingList(
            @PathVariable("tourId") String tourId
    ) throws JsonProcessingException {
        return SuccessEntityResponse.OkResponse("Lấy tổng số rating thành công",this.reviewService.getRatingTotal(tourId));
    }

    @PostMapping(value = "/create",consumes = "multipart/form-data")
    public  MessageResponse<ReviewResponse> createReview(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("data") String data
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewRequest review = objectMapper.readValue(data, ReviewRequest.class);
        ReviewResponse reviewResponse = reviewService.addReview(files,review);
        return MessageResponse.<ReviewResponse>builder()
                .statusCode(200)
                .success(true)
                .data(reviewResponse)
                .message("Tạo review thanh cong")
                .build();
    };
}
