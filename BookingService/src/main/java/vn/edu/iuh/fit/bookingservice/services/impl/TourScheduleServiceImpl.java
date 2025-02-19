package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.TourScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourScheduleServiceImpl implements TourScheduleService {
    private final TourScheduleRepository tourScheduleRepository;
    private final TourScheduleMapper tourScheduleMapper;

    public TourScheduleServiceImpl(TourScheduleRepository tourScheduleRepository,TourScheduleMapper tourScheduleMapper){
        this.tourScheduleRepository = tourScheduleRepository;
        this.tourScheduleMapper = tourScheduleMapper;
    }
    @Override
    public List<TourScheduleResponse> getAllTourSchedules() {
         List<TourSchedule> tourSchedules = tourScheduleRepository.findAll();
         if(tourSchedules.size() == 0) throw new NotFoundException("Không tìm thấy lịch tour nào");
        List<TourScheduleResponse> tourScheduleResponseList = tourSchedules.stream().map(tour->tourScheduleMapper.entityToResponse(tour)).collect(Collectors.toList());
         return tourScheduleResponseList;
    }

    @Override
    public TourScheduleResponse getTourScheduleById(String tourScheduleId) {
        TourSchedule foundTourSchedule = tourScheduleRepository.findById(tourScheduleId).orElse(null);
        if(foundTourSchedule == null) throw new NotFoundException("Không tìm thấy lịch tour đấy");
        return tourScheduleMapper.entityToResponse(foundTourSchedule);
    }

    @Override
    public TourScheduleResponse createTourSchedule(TourScheduleRequest tourScheduleRequest) {
        TourSchedule tourSchedule  = tourScheduleMapper.requestToEntity(tourScheduleRequest);
        tourScheduleRepository.save(tourSchedule);
        return tourScheduleMapper.entityToResponse(tourSchedule);
    }

    @Override
    public TourScheduleResponse updateTourScheduleById() {
        return null;
    }

    @Override
    public boolean deleteTourScheduleById(String idTourSchedule) {
        return false;
    }

    @Override
    public boolean deleteAllTicketByTourScheduleId() {
        return false;
    }
}
