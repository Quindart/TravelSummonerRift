package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;

@Mapper(componentModel = "spring")
public interface TourDestinationMapper {
    TourDestination toTourDestination(TourDestinationRequest tourDestinationRequest);
    @Mapping(target = "tourDestinationId",source = "tourDestinationId")
    TourDestinationResponse toTourDestinationResponse(TourDestination tourDestination);
}
