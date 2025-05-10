package vn.edu.iuh.fit.bookingservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.FileReviewDto;
import vn.edu.iuh.fit.bookingservice.dtos.responses.RatingTotalResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.ReviewMapper;
import vn.edu.iuh.fit.bookingservice.repositories.ReviewRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.repositories.httpclient.UserServiceClient;
import vn.edu.iuh.fit.bookingservice.services.CloudinaryService;
import vn.edu.iuh.fit.bookingservice.services.IAuthData;
import vn.edu.iuh.fit.bookingservice.services.ReviewService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CloudinaryService cloudinaryService;
    private final TourScheduleRepository tourScheduleRepository;
    private final UserServiceClient userServiceClient;
    private final IAuthData authData;
    private final RedisService redisService;

    @Override
    public List<ReviewResponse> getReviewByTourId(int page, int size, String sortBy, String direction, String tour_id) {

        Sort.Direction sortDirection = "ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;


        Pageable pageable = PageRequest.of(page , size, Sort.by(sortDirection, sortBy));
        List<Object[]> result = reviewRepository.getReviewsByTourId(tour_id, pageable);
        return result.stream()
                .map(objects -> new ReviewResponse(
                        (String) objects[0],
                        ((Timestamp) objects[1]).toLocalDateTime(),
                        (String) objects[2],
                        (String) objects[3],
                        (String) objects[4],
                        (Float) objects[5],
                        (String) objects[6],
                        handleConvertStringToListFileReview((String)objects[7])
                ))
                .collect(Collectors.toList());
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
    public List<RatingTotalResponse> getRatingTotal(String tourId) throws JsonProcessingException {
        String findRedisKey = "RATING:"+tourId;
        List<RatingTotalResponse> foundList = this.redisService.getValue(findRedisKey, new TypeReference<List<RatingTotalResponse>>() {
        });
        if(foundList != null) {
            System.out.printf("DJEMEMEMM");
            return foundList;
        };
        List<RatingTotalResponse> ratingList = this.reviewRepository.getTotalRatingOfTour(tourId)
                .stream().map((object)-> new RatingTotalResponse((Float) object[0], Math.toIntExact((Long) object[1]))).toList();
        try {
            this.redisService.setValue(findRedisKey,ratingList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ratingList;
    }


    private List<FileReviewDto> handleConvertStringToListFileReview(String stringListFileReview){
        List<FileReviewDto> result = new LinkedList<>();
        String[] stringListData = stringListFileReview.split(",");
        for (String stringData:stringListData){
            String[] keyValue = stringData.split("Order");
            String fileUrl = keyValue[0];
            int fileOrder = Integer.parseInt(keyValue[1]);
            result.add(new FileReviewDto(fileUrl,fileOrder));
        }
        return result;
    }
}