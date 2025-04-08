package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;

import java.util.List;
@Service
public interface ReviewService {
    List<ReviewResponse> getReviews();
    ReviewResponse getReview(String id);
    List<ReviewResponse>  getReviewByTourId(String tourId);

    ReviewResponse addReview(ReviewRequest review);
    ReviewResponse updateReview(ReviewRequest review);
    void deleteReview(String id);
}
