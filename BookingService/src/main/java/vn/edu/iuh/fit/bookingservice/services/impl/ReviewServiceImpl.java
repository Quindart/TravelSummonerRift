package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.mapper.ReviewMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.ReviewRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private TourScheduleMapper tourScheduleMapper;

    @Autowired
    private TourRepository tourRepository;

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
    public  List<ReviewResponse>  getReviewByTourId(String tourId) {
        Tour current_tour = tourRepository.findById(tourId).orElseThrow();
        List<String> tourScheduleIds = new ArrayList<>();

        current_tour.getTourSchedules().stream().forEach(tour-> {
            tourScheduleIds.add(tour.getTourScheduleId());
        });
        List<Review> reviewSaved = new ArrayList<>();
        for (String tourScheduleId : tourScheduleIds) {
            List<Review> reviews = reviewRepository.findByTourScheduleId(tourScheduleId);

            reviews.stream().forEach(review -> {
                 reviewSaved.add(review);
            });
        }
        return reviewSaved.stream().map(reviewMapper::toReviewResponse).toList();
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