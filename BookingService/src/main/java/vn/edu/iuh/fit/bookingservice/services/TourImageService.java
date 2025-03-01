package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;

import java.io.IOException;

public interface TourImageService {
    TourImageResponse saveTourImage(TourImageRequest tourImageRequest);
    TourImageResponse updateTourImage(String tourImageId, TourImageRequest tourImageRequest, MultipartFile file);
    void deleteTourImage(String tourImageId) throws IOException;
}
