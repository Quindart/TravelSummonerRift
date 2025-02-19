package vn.edu.iuh.fit.bookingservice.services;


import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

import java.util.List;

public interface TourScheduleService {
    List<TourScheduleResponse> getAllTourSchedules();
    TourScheduleResponse getTourScheduleById(String id);
    TourScheduleResponse createTourSchedule(TourScheduleRequest tourScheduleRequest);
    TourScheduleResponse updateTourScheduleById();
    boolean deleteTourScheduleById(String idTourSchedule);
    boolean deleteAllTicketByTourScheduleId();
}
