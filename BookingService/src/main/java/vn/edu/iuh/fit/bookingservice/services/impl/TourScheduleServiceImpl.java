package vn.edu.iuh.fit.bookingservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.TourScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourScheduleServiceImpl implements TourScheduleService {
    private final TourScheduleRepository tourScheduleRepository;
    private final TourScheduleMapper tourScheduleMapper;
    private static final Logger logger = LoggerFactory.getLogger(TourScheduleServiceImpl.class);
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
    public TourScheduleResponse updateTourSchedule(TourScheduleRequest tourScheduleRequestDTO) {
        try {
            TourSchedule tourSchedule = this.tourScheduleRepository.findByTourScheduleId(tourScheduleRequestDTO.getTourScheduleId()).orElseThrow(
                    ()-> {throw new NotFoundException("Không tim thấy tour schedule này");}
            );
            tourSchedule.setName(tourScheduleRequestDTO.getName());
            tourSchedule.setDescription(tourScheduleRequestDTO.getDescription());
            tourSchedule.setStartDate(tourScheduleRequestDTO.getStartDate());
            tourSchedule.setEndDate(tourScheduleRequestDTO.getEndDate());
            tourSchedule.setAdultPrice(tourScheduleRequestDTO.getAdultPrice());
            tourSchedule.setChildPrice(tourScheduleRequestDTO.getChildPrice());
            tourSchedule.setBabyPrice(tourScheduleRequestDTO.getBabyPrice());
            tourSchedule.setSlot(tourScheduleRequestDTO.getSlot());
            return tourScheduleMapper.entityToResponse(tourScheduleRepository.save(tourSchedule));

        }catch (Exception exception){
            throw new InternalServerErrorException("Update tourschedule error");
        }
    }


    @Override
    public boolean deleteTourScheduleById(String idTourSchedule) {
        int result =  tourScheduleRepository.updateIsActiveByTourScheduleId(false,idTourSchedule);
        if(result != 0) return true;
        throw new NotFoundException("Không tồn tại tour schedule để xóa");
    }

    @Override
    public boolean deleteAllTicketByTourScheduleId() {
        return false;
    }
}
