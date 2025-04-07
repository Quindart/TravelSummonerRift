package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.mapper.ReviewMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.ReviewRepository;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private TourScheduleMapper tourScheduleMapper;
    @Override
    public List<ReviewResponse> getReviews() {
        List<ReviewResponse> reviewResponses = reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
        for (ReviewResponse reviewResponse : reviewResponses) {
            Review review = reviewRepository.findById(reviewResponse.getReviewId())
                    .orElseThrow(() -> new RuntimeException("Review not found"));
            TourScheduleResponse tourScheduleResponse = tourScheduleMapper.entityToResponse(review.getTourSchedule());
            reviewResponse.setTourScheduleResponse(tourScheduleResponse);
        }
        return reviewResponses;
    }

    @Override
    public ReviewResponse getReview(String id) {
        return null;
    }

    @Override
    public ReviewResponse addReview(ReviewRequest review) {
        return null;
    }

    @Override
    public ReviewResponse updateReview(ReviewRequest review) {
        return null;
    }

    @Override
    public void deleteReview(String id) {

    }
}