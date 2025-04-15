package vn.edu.iuh.fit.bookingservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.Infra.client.IUserClient;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourHistory;
import vn.edu.iuh.fit.bookingservice.mapper.TourHistoryMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourHistoryRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.TourHistoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourHistoryServiceImpl implements TourHistoryService {

    private final TourRepository tourRepository;
    private final TourHistoryRepository tourHistoryRepository;
    private final IUserClient userClient;
    private final TourHistoryMapper tourHistoryMapper;

    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void addTourToHistory(String userId, String tourId) {
        // Kiểm tra tour tồn tại
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại"));

        // Kiểm tra user tồn tại
        try {
            userClient.getUserById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại");
        }

        // Kiểm tra xem đã có lịch sử xem tour này chưa
        Optional<TourHistory> existingHistory = tourHistoryRepository.findByUserIdAndTourTourId(userId, tourId);

        TourHistory tourHistory;
        if (existingHistory.isPresent()) {
            // Nếu đã có, cập nhật thời gian xem
            tourHistory = existingHistory.get();
            tourHistory.setViewDate(LocalDateTime.now());
        } else {
            // Nếu chưa có, tạo mới
            tourHistory = new TourHistory();
            tourHistory.setUserId(userId);
            tourHistory.setTour(tour);
            tourHistory.setViewDate(LocalDateTime.now());

            // Kiểm tra số lượng lịch sử
            List<TourHistory> histories = tourHistoryRepository.findByUserIdOrderByViewDateDesc(userId);
            if (histories.size() >= MAX_HISTORY_SIZE) {
                // Xóa lịch sử cũ nhất
                List<TourHistory> oldestHistories = tourHistoryRepository.findOldestByUserId(userId);
                if (!oldestHistories.isEmpty()) {
                    tourHistoryRepository.delete(oldestHistories.get(0));
                }
            }
        }

        tourHistoryRepository.save(tourHistory);
    }

    @Override
    public List<TourHistoryResponse> getUserTourHistory(String userId) {
        List<TourHistory> histories = tourHistoryRepository.findByUserIdOrderByViewDateDesc(userId);
        return tourHistoryMapper.toHistoryDtoList(histories);
    }
}
