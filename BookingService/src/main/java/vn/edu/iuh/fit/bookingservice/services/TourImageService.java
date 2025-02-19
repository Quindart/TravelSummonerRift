package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;

import java.io.IOException;

public interface TourImageService {
    TourImageResponse saveTourImage(MultipartFile file, TourImageRequest tourImageRequest);
    TourImageResponse updateTourImage(String tourImageId, TourImageRequest tourImageRequest);
    void deleteTourImage(String tourImageId) throws IOException;
}
