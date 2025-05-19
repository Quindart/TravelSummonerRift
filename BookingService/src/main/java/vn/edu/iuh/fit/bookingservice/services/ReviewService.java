package vn.edu.iuh.fit.bookingservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.RatingResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.RatingTotalResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;

import java.io.IOException;
import java.util.List;
@Service
public interface ReviewService {

    List<ReviewResponse>  getReviewByTourId(int page, int size, String sortBy, String direction,String tour_id);

    ReviewResponse addReview(MultipartFile[] files,ReviewRequest review) throws IOException;

    RatingTotalResponse getRatingTotal(String tourId) throws JsonProcessingException;
}
