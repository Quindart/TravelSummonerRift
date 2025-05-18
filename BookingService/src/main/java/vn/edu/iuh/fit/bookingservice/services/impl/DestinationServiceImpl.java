package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.DestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.DestinationResponse;
import vn.edu.iuh.fit.bookingservice.entities.Destination;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.DestinationMapper;
import vn.edu.iuh.fit.bookingservice.repositories.DestinationRepository;
import vn.edu.iuh.fit.bookingservice.services.DestinationService;

import java.util.List;

@Service
public class DestinationServiceImpl  implements DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private DestinationMapper destinationMapper;

    @Override
    public List<DestinationResponse> getAllDestination() {
        return destinationRepository.findAll().stream()
                .map(destinationMapper::toDestinationResponse)
                .toList();
    }

    @Override
    public DestinationResponse getDestinationById(String destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy điểm đến"));

        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public DestinationResponse createDestination(DestinationRequest destinationRequest) {

        Destination destination = destinationMapper.toDestination(destinationRequest);
        destination = destinationRepository.save(destination);
        if (destination == null) {
            throw new NotFoundException("Tạo điểm đến thất bại");
        }
        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public DestinationResponse updateDestination(String destinationId, DestinationRequest destinationRequest) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy điểm đến"));
        destination.setName(destinationRequest.getName());
        destination.setDescription(destinationRequest.getDescription());
        destination.setCity(destinationRequest.getCity());
        destination.setCountry(destinationRequest.getCountry());
        destination.setAddress(destinationRequest.getAddress());
        destination.setCityId(destinationRequest.getCityId());
        destination.setDistrictId(destinationRequest.getDistrictId());

        destination = destinationRepository.save(destination);
        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public void deleteDestination(String destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy điểm đến"));
       destinationRepository.delete(destination);
    }
}
