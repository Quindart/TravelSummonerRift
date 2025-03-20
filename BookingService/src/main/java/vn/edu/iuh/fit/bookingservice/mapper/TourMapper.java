package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

@Mapper(componentModel = "spring")
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);
    TourResponse toTourResponse(Tour tour);

    TourOverviewResponse toTourOverviewResponse(Tour tour);

    TourScheduleDetailResponse toTourScheduleDetailResponse(TourSchedule tourSchedule);

    TourImageResponse toTourImageResponse(TourImage tourImage);

    TourNoteResponse toTourNoteResponse(TourNote tourNote);
}
