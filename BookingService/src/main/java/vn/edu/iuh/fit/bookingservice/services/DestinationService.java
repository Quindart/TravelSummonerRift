package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.DestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.DestinationResponse;

import java.util.List;

public interface DestinationService {
    List<DestinationResponse> getAllDestination();
    DestinationResponse getDestinationById(String destinationId);
    DestinationResponse createDestination(DestinationRequest destinationRequest);
    DestinationResponse updateDestination(String destinationId, DestinationRequest destinationRequest);
    void deleteDestination(String destinationId);
}
