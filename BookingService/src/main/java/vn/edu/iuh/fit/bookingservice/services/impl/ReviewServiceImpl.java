package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.FileReviewDto;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.SortFieldReview;
import vn.edu.iuh.fit.bookingservice.exception.errors.BadRequestException;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     *
     * @param page page order
     * @param size  size object/page
     * @param sortBy field
     * @param direction asc/desc
     * @return
     */

    @Override
    public List<ReviewResponse> getReviewByTourId(int page, int size, String sortBy, String direction, String tour_id) {
//        this.tourRepository.findToursByTourId(tour_id).orElseThrow(()->new NotFoundException("Không tìm thấy tour này"));
//        int offset = page * size;
//        Sort.Direction sortDirection = "ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//        Pageable pageable = PageRequest.of(offset / size, size, Sort.by(sortDirection, sortBy));
//        List<Object[]> result = reviewRepository.getReviewsByTourId(tour_id, sortBy, direction, size, offset,pageable);
        // Xác định hướng sắp xếp
        Sort.Direction sortDirection = "ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Xác định đối tượng Pageable
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