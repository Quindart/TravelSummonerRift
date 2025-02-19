package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.mapper.TourImageMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourImageRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.CloudinaryService;
import vn.edu.iuh.fit.bookingservice.services.TourImageService;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TourImageServiceImpl implements TourImageService {

    private final CloudinaryService cloudinaryService;
    private final TourImageRepository tourImageRepository;
    private final TourImageMapper tourImageMapper;
    private final TourRepository tourRepository;

    @Autowired
    public TourImageServiceImpl(CloudinaryService cloudinaryService,
                                TourImageRepository tourImageRepository,
                                TourImageMapper tourImageMapper,
                                TourMapper tourMapper, TourMapper tourMapper1, TourRepository tourRepository
    ) {
        this.cloudinaryService = cloudinaryService;
        this.tourImageRepository = tourImageRepository;
        this.tourImageMapper = tourImageMapper;
        this.tourRepository = tourRepository;
    }

    @Override
    public TourImageResponse saveTourImage(MultipartFile file, TourImageRequest tourImageRequest) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file);

            Optional<Tour> tour = tourRepository.findById(tourImageRequest.getTourId());
            if (tour.isEmpty()) {
                throw new RuntimeException("Tour không tồn tại với ID: " + tourImageRequest.getTourId());
            }

            TourImage tourImage = tourImageMapper.toTourImage(tourImageRequest);
            tourImage.setTour(tour.get());
            tourImage.setImageUrl(imageUrl);
            tourImage.setCreatedAt(LocalDateTime.now());
            tourImage.setActive(true);

            TourImage savedTourImage = tourImageRepository.save(tourImage);

            return tourImageMapper.toTourImageResponse(savedTourImage);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh lên Cloudinary: " + e.getMessage(), e);
        }
    }


    @Override
    public TourImageResponse updateTourImage(String tourImageId, TourImageRequest tourImageRequest) {
        TourImage existingImage = tourImageRepository.findById(tourImageId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ảnh với ID: " + tourImageId));

        // Cập nhật thông tin ảnh
        existingImage.setDescription(tourImageRequest.getDescription());
        existingImage.setOrderIndex(tourImageRequest.getOrderIndex());
        existingImage.setUpdatedAt(LocalDateTime.now());

        // Lưu vào DB
        TourImage updatedImage = tourImageRepository.save(existingImage);

        return tourImageMapper.toTourImageResponse(updatedImage);
    }

    @Override
    public void deleteTourImage(String tourImageId) throws IOException {
        TourImage existingImage = tourImageRepository.findById(tourImageId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ảnh với ID: " + tourImageId));

        // Xóa ảnh trên Cloudinary
        cloudinaryService.deleteImage(existingImage.getImageUrl());

        // Xóa khỏi DB
        tourImageRepository.delete(existingImage);
    }
}
