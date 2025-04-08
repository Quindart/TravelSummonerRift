package vn.edu.iuh.fit.userservice.repositories.httpclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import vn.edu.iuh.fit.userservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.ReviewResponse;

@FeignClient(name = "booking-service", url = "http://localhost:1902/booking-service/reviews")
public interface BookingServiceClient {

    ReviewResponse createReview(ReviewRequest review);

}
