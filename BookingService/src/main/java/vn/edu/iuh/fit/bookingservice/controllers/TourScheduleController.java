package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourScheduleRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourScheduleResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.impl.TourScheduleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/tour-schedules")
public class TourScheduleController {
    public TourScheduleServiceImpl tourScheduleServiceImp;
    public  TourScheduleController(TourScheduleServiceImpl tourScheduleServiceImp){
        this.tourScheduleServiceImp = tourScheduleServiceImp;
    }
    @GetMapping
    public ResponseEntity<MessageResponse<List<TourScheduleResponse>>> getAllTourSchedules(){
        return SuccessEntityResponse.FoundResponse("Danh sách tất cả ",tourScheduleServiceImp.getAllTourSchedules());
    }

    @GetMapping("/{tourScheduleId}")
    public ResponseEntity<MessageResponse<TourScheduleResponse>> getTourScheduleById(@PathVariable String tourScheduleId){
        return SuccessEntityResponse.FoundResponse("Tìm thấy id ",tourScheduleServiceImp.getTourScheduleById(tourScheduleId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<TourScheduleResponse>>  createTourSchedule(@RequestBody  TourScheduleRequest tourScheduleRequestDTO){
        return SuccessEntityResponse.CreateResponse("Tạo thành công",tourScheduleServiceImp.createTourSchedule(tourScheduleRequestDTO));
    }

    @PutMapping("/{tourScheduleId}")
    public ResponseEntity<MessageResponse<TourScheduleResponse>> updateTourScheduleById(@RequestBody TourScheduleRequest tourScheduleRequestDTO,@PathVariable String tourScheduleId){
        return null;
    }

    @DeleteMapping("/{tourScheduleId}")
    public ResponseEntity<MessageResponse<TourScheduleResponse>> deleteTourScheduleById(@PathVariable String tourScheduleId){
        return null;
    }

    @DeleteMapping("/{tourScheduleId}/tickets")
    public ResponseEntity<MessageResponse<TourScheduleResponse>> deleteAllTicketByTourScheduleId(@PathVariable String tourScheduleId){
        return null;
    }
}
