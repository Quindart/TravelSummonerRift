package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.ReviewMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.ReviewRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.repositories.httpclient.UserServiceClient;
import vn.edu.iuh.fit.bookingservice.services.CloudinaryService;
import vn.edu.iuh.fit.bookingservice.services.IAuthData;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private TourScheduleMapper tourScheduleMapper;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private IAuthData authData;

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
    public ReviewResponse addReview(MultipartFile[] files, ReviewRequest review) throws IOException {
        PrincipalAuthentication auth = this.authData.getAuth();
        String userId = auth.getUserId();

         UserResponse foundUser = this.userServiceClient.getUserById(userId).getData();


        TourSchedule foundTourSchedule = this.tourScheduleRepository.findById(review.getTourScheduleId()).orElseThrow(()->new NotFoundException("Không tìm thấy tour schedule"));
        List<String> linkFiles = new LinkedList<>();
        for(MultipartFile file:files){
            String link = this.cloudinaryService.uploadImage(file);
            linkFiles.add(link);
        }
        Review modelReview = Review.builder()
            .reviewDate(LocalDateTime.now())
                .userId(foundUser.getUserId())
                .username(foundUser.getUsername())
                .rating(review.getRating())
                .content(review.getContent())
                .files(linkFiles)
                .tourSchedule(foundTourSchedule)
        .build();
        Review savedResult = this.reviewRepository.save(modelReview);

        return reviewMapper.toReviewResponse(savedResult);
    }


    @Override
    public ReviewResponse updateReview(ReviewRequest review) {
        return null;
    }

    @Override
    public void deleteReview(String id) {

    }
}