package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);
    TourResponse toTourResponse(Tour tour);
}
