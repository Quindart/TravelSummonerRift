package vn.edu.iuh.fit.bookingservice.services;


import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;

import java.util.List;

public interface TourFavoriteService {
    void addTourToFavorite(String userId, String tourId);
    List<TourFavoriteResponse> getAllFavoritesByUserId(String userId);
    void updateTourFavorite(String userId, List<String> tourIds);
}
