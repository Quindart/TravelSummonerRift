package vn.edu.iuh.fit.bookingservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.Infra.client.IUserClient;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.repositories.TourFavoriteRepository;

@Service
@RequiredArgsConstructor
public class TourFavoriteService implements vn.edu.iuh.fit.bookingservice.services.TourFavoriteService {
    private final IUserClient userClient;
    private final TourFavoriteRepository tourFavoriteRepository;

    @Override
    public void addTourToFavorite(String userId, String tourId) {
        MessageResponse<UserResponse> userResponse = userClient.getUserById(userId);

        if (userResponse == null || !userResponse.isSuccess()) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }

        // Nếu tồn tại người dùng, tiếp tục logic lưu tour yêu thích
        UserResponse user = userResponse.getData();

        TourFavorite favorite = new TourFavorite();
        favorite.setUserId(Long.parseLong(user.getUserId()));
        favorite.setTour(tourId);
        tourFavoriteRepository.save(favorite);
    }
}
