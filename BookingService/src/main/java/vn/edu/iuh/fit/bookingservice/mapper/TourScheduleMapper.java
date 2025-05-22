package vn.edu.iuh.fit.bookingservice.mapper;

import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.edu.iuh.fit.bookingservice.dtos.TourScheduleDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

import java.util.List;

@Mapper(componentModel = "spring")

public interface TourScheduleMapper {
    //    Entity to ResponseDTO
    @Mapping(source = "tour.tourId",target = "tourId")
    TourScheduleResponse entityToResponse (TourSchedule tourSchedule);
    //    Request to Entity
    @Mapping(source = "tourId", target = "tour", qualifiedByName = "IdToTour")
    TourSchedule requestToEntity (TourScheduleRequest tourScheduleRequest);

    @Named("IdToTour")
    default Tour IdToTour(String tourId){
        Tour tour = new Tour();
        tour.setTourId(tourId);
        return  tour;
    }

    @Mapping(source = "tour.tourId",target = "tourId")
    List<TourScheduleResponse> entityToResponseList (List<TourSchedule> tourScheduleList);
}
