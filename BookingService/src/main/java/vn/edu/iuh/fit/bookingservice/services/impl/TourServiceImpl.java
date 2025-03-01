package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper tourMapper;

    @Override
    public List<TourResponse> getAllTours() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }

    @Override
    public TourResponse getTourById(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));

        return tourMapper.toTourResponse(tour);
    }

    @Override
    public TourResponse createTour(TourRequest tourRequest) {
        if (tourRequest.getName() == null || tourRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Tên tour không được để trống");
        }

        Tour tour = tourMapper.toTour(tourRequest);
        Tour savedTour = tourRepository.save(tour);
        if (savedTour == null) {
            throw new InternalServerErrorException("Tạo tour thất bại");
        }
        return tourMapper.toTourResponse(savedTour);
    }

    @Override
    public TourResponse updateTour(String tourId, TourRequest tourRequest) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));

        tour.setName(tourRequest.getName());
        tour.setDescription(tourRequest.getDescription());
        tour.setPrice(tourRequest.getPrice());
        tour.setDuration(tourRequest.getDuration());
        tour.setThumbnail(tourRequest.getThumbnail());

        Tour updatedTour = tourRepository.save(tour);

        return tourMapper.toTourResponse(updatedTour);
    }

    @Override
    public void deleteTour(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));

        tour.setActive(false);
        tourRepository.save(tour);
    }
}
