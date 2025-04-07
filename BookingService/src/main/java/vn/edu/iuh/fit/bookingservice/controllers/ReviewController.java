package vn.edu.iuh.fit.bookingservice.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse<List<ReviewResponse>>> getAllReviews() {
        return SuccessEntityResponse.FoundResponse("Get all reviews success", reviewService.getReviews());
    }
    @PostMapping("/create")
    public  MessageResponse<ReviewResponse> createReview(@RequestBody ReviewRequest review) {
       ReviewResponse reviewResponse = reviewService.addReview(review);
        return MessageResponse.<ReviewResponse>builder()
                .statusCode(200)
                .success(true)
                .data(reviewResponse)
                .message("Tạo review thanh cong")
                .build();
    };
}
