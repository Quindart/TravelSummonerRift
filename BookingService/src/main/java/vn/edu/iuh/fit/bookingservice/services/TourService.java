package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;

import java.util.List;

public interface TourService {

    List<TourResponse> getAllTours();

    TourResponse getTourById(String tourId);

    TourResponse createTour(TourRequest tourRequest);

    TourResponse updateTour(String tourId, TourRequest tourRequest);

    void deleteTour(String tourId);

    List<TourResponse> searchTours(String tourName, String category, Double minPrice, Double maxPrice, String city, String destination);

    List<TourOverviewResponse> getAllTourOverviews();

    TourScheduleDetailResponse getTourScheduleDetail(String tourId, String scheduleId);

    List<TourImageResponse> getTourImages(String tourId);

    List<TourNoteResponse> getTourNotes(String tourId);
}
