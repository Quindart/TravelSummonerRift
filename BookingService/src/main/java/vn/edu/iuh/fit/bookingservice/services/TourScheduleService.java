package vn.edu.iuh.fit.bookingservice.services;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

import java.util.List;

public interface TourScheduleService {
    List<TourScheduleResponse> getAllTourSchedules();
    TourScheduleResponse getTourScheduleById(String id);
    TourScheduleResponse createTourSchedule(TourScheduleRequest tourScheduleRequest);
    TourScheduleResponse updateTourSchedule( TourScheduleRequest tourScheduleRequestDTO);
    boolean deleteTourScheduleById(String idTourSchedule);
    boolean deleteAllTicketByTourScheduleId();
}
