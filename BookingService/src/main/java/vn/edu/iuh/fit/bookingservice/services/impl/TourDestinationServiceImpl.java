package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;
import vn.edu.iuh.fit.bookingservice.mapper.TourDestinationMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourDestinationRepository;
import vn.edu.iuh.fit.bookingservice.services.TourDestinationService;

import java.util.List;

@Service
public class TourDestinationServiceImpl implements TourDestinationService {

    @Autowired
    private TourDestinationRepository tourDestinationRepository;
    @Autowired
    private TourDestinationMapper tourDestinationMapper;

    @Override
    public List<TourDestinationResponse> getAllTourDestination() {
        return tourDestinationRepository.findAll()
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
    public TourDestinationResponse createTourDestination(TourDestinationRequest tourDestinationRequest) {
        TourDestination tourDestination =  tourDestinationMapper.toTourDestination(tourDestinationRequest);
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
