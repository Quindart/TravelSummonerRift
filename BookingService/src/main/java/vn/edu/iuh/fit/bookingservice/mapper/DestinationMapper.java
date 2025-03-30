package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import vn.edu.iuh.fit.bookingservice.dtos.requests.DestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.DestinationResponse;
import vn.edu.iuh.fit.bookingservice.entities.Destination;

@Mapper(componentModel = "spring")
public interface DestinationMapper {
    Destination toDestination(DestinationRequest destinationRequest);
    DestinationResponse toDestinationResponse(Destination destination);
}
