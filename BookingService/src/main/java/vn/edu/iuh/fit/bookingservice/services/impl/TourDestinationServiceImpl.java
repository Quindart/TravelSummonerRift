package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.entities.Destination;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourDestinationMapper;
import vn.edu.iuh.fit.bookingservice.repositories.DestinationRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourDestinationRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.DestinationService;
import vn.edu.iuh.fit.bookingservice.services.TourDestinationService;

import java.util.List;

@Service
public class TourDestinationServiceImpl implements TourDestinationService {

    @Autowired
    private TourDestinationRepository tourDestinationRepository;
    @Autowired
    private TourDestinationMapper tourDestinationMapper;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private DestinationRepository destinationRepository;

    @Override
    public List<TourDestinationResponse> getAllTourDestination() {
        return tourDestinationRepository.findAll()
                .stream().map(tourDestinationMapper::toTourDestinationResponse)
                .toList();
    }

    @Override
    public List<TourDestinationResponse> getAllTourDestinationByTourId(String tourId) {
        return tourDestinationRepository.findByTour_TourId(tourId)
                .stream().map(tourDestinationMapper::toTourDestinationResponse)
                .toList();
    }

    @Override
    public TourDestinationResponse getTourDestinationById(String tourDestinationId) {
        return tourDestinationRepository.findById(tourDestinationId)
                .map(tourDestinationMapper::toTourDestinationResponse)
                .orElse(null);
    }



    @Override
    public TourDestinationResponse createTourDestination(TourDestinationRequest request) {
        Tour tour = tourRepository.findById(request.getTourId()).orElseThrow(() -> new NotFoundException("Tour không tồn tại"));
        Destination destination = destinationRepository.findById(request.getDestinationId()).orElseThrow(() -> new NotFoundException("Không tìm thấy Destiantion"));
        TourDestination tourDestination =  tourDestinationMapper.toTourDestination(request);
        tourDestination.setTour(tour);
        tourDestination.setDestination(destination);
        TourDestination savedTourDestination = tourDestinationRepository.save(tourDestination);
        return tourDestinationMapper.toTourDestinationResponse(savedTourDestination);
    }

    @Override
    public TourDestinationResponse updateTourDestination(String tourDestinationId, TourDestinationRequest tourDestination) {
        TourDestination tourDestination1 = tourDestinationRepository.findById(tourDestinationId)
                .orElse(null);
        if (tourDestination1 == null) {
            return null;
        }

        tourDestination1.setName(tourDestination.getName());
        tourDestination1.setDescription(tourDestination.getDescription());

        tourDestinationRepository.save(tourDestination1);
        return tourDestinationMapper.toTourDestinationResponse(tourDestination1);
    }

    @Override
    public void deleteTourDestination(String tourDestinationId) {
        tourDestinationRepository.deleteById(tourDestinationId);
    }

}
