package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;

import java.util.List;

public interface TourService {

    List<TourResponse> getAllTours();

    TourResponse getTourById(String tourId);

    TourResponse createTour(TourRequest tourRequest);

    TourResponse updateTour(String tourId, TourRequest tourRequest);

    void deleteTour(String tourId);
}
