package vn.edu.iuh.fit.bookingservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.Infra.client.IUserClient;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.mapper.TourFavoriteMapperImpl;
import vn.edu.iuh.fit.bookingservice.repositories.TourFavoriteRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.TourFavoriteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourFavoriteServiceImpl implements TourFavoriteService {

    private final TourRepository tourRepository;
    private final TourFavoriteRepository tourFavoriteRepository;
    private final IUserClient userClient;
    private final TourFavoriteMapperImpl tourFavoriteMapper;

    @Override
    public void addTourToFavorite(String userId, String tourId) {

        // Kiểm tra tour tồn tại
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại"));

        // Kiểm tra user tồn tại
        try {
            userClient.getUserById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại");
        }

        // Tạo mới TourFavorite
        TourFavorite tourFavorite = new TourFavorite();
        tourFavorite.setUserId(userId); // Đảm bảo đã set userId
        tourFavorite.setTour(tour);

        tourFavoriteRepository.save(tourFavorite);
    }

    @Override
    public List<TourFavoriteResponse> getAllFavoritesByUserId(String userId) {
        List<TourFavorite> favorites = tourFavoriteRepository.findByUserId(userId);
        return tourFavoriteMapper.toFavoriteDtoList(favorites);
    }
}
