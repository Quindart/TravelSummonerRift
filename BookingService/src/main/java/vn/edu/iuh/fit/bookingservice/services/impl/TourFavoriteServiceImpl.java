package vn.edu.iuh.fit.bookingservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;
import vn.edu.iuh.fit.bookingservice.mapper.impl.TourFavoriteMapperImpl;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.repositories.TourFavoriteRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.repositories.httpclient.UserServiceClient;
import vn.edu.iuh.fit.bookingservice.services.TourFavoriteService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourFavoriteServiceImpl implements TourFavoriteService {

    private final TourRepository tourRepository;
    private final TourFavoriteRepository tourFavoriteRepository;
    private final UserServiceClient userClient;
    private final TourFavoriteMapperImpl tourFavoriteMapper;

    @Override
    public void addTourToFavorite(String userId, String tourId) {

        // Kiểm tra tour tồn tại
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Tour không tồn tại"));

        // Kiểm tra user tồn tại

        userClient.getUserById(userId);


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

    @Override
    public void updateTourFavorite(String userId, List<String> tourIds) {

            userClient.getUserById(userId);

        
        // Xóa tất cả favorites hiện tại của user
        List<TourFavorite> currentFavorites = tourFavoriteRepository.findByUserId(userId);
        tourFavoriteRepository.deleteAll(currentFavorites);
        
        // Thêm mới các favorites từ danh sách tour IDs
        for (String tourId : tourIds) {
            Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại: " + tourId));
            
            TourFavorite tourFavorite = new TourFavorite();
            tourFavorite.setUserId(userId);
            tourFavorite.setTour(tour);
            
            tourFavoriteRepository.save(tourFavorite);
        }
    }

    @Override
    public void deleteTourFavorite(String userId, String tourId) {
        try {
            userClient.getUserById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại");
        }

        // Kiểm tra tour tồn tại
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại"));

        // Kiểm tra tour favorite tồn tại
        Optional<TourFavorite> tourFavorite = tourFavoriteRepository.findByUserIdAndTour_TourId(userId, tourId);

        if (tourFavorite.isPresent()) {
            tourFavoriteRepository.delete(tourFavorite.get());
        } else {
            throw new RuntimeException("Tour favorite không tồn tại");
        }
    }
}
