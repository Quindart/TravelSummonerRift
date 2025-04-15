package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;

import java.util.List;

public interface TourHistoryService {
    void addTourToHistory(String userId, String tourId);
    List<TourHistoryResponse> getUserTourHistory(String userId);
}
