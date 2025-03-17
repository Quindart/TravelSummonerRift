package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;

import java.util.List;

public interface TourDestinationService {
    List<TourDestinationResponse> getAllTourDestination();
    TourDestinationResponse getTourDestinationById(String tourDestinationId);
    TourDestinationResponse createTourDestination(TourDestinationRequest tourDestination);
    TourDestinationResponse updateTourDestination(String tourDestinationId, TourDestinationRequest tourDestination);
    void deleteTourDestination(String tourDestinationId);
}
