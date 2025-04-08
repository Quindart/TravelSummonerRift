package vn.edu.iuh.fit.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.userservice.repositories.httpclient.BookingServiceClient;

@Service
public class ReviewService implements BookingServiceClient {
    @Autowired
    private BookingServiceClient bookingServiceClient;
    @Override
    public ReviewResponse createReview(ReviewRequest review) {
        ReviewResponse reviewResponse =  bookingServiceClient.createReview(review);

        return null;
    }
}
