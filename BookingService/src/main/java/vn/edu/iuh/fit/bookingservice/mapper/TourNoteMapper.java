package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.iuh.fit.bookingservice.dtos.requests.CategoryTourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourNoteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourNoteResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;

@Mapper(componentModel = "spring")
public interface TourNoteMapper {
    TourNote toTourNote(TourNoteRequest tourNoteRequest);

    @Mapping(source = "tour.tourId", target = "tourId")
    TourNoteResponse toTourNoteResponse(TourNote tourNote);

}
