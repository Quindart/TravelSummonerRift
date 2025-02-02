package vn.edu.iuh.fit.bookingservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourNoteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourNoteResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TourNoteService;

import java.util.List;

@RestController
@RequestMapping("/api/tour-notes")
public class TourNoteController {
    @Autowired
    private TourNoteService tourNoteService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<TourNoteResponse>>> getAllTourNotes(){
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", tourNoteService.getAllTourNote());
    }

    @GetMapping("/{tourNoteId}")
    public ResponseEntity<MessageResponse<TourNoteResponse>> getTourNoteById(@PathVariable String tourNoteId){
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", tourNoteService.getTourNoteById(tourNoteId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<TourNoteResponse>> createTourNote(@RequestBody @Valid TourNoteRequest tourNoteRequest){
        return SuccessEntityResponse.CreateResponse("Tạo tour note thành công", tourNoteService.createTourNote(tourNoteRequest));
    }

    @PutMapping("/{tourNoteId}")
    public ResponseEntity<MessageResponse<TourNoteResponse>> updateTourNote(@PathVariable String tourNoteId, @RequestBody @Valid TourNoteRequest tourNoteRequest){
        return SuccessEntityResponse.OkResponse("Cập nhật tour note thành công", tourNoteService.updateTourNote(tourNoteId, tourNoteRequest));
    }

    @DeleteMapping("/{tourNoteId}")
    public ResponseEntity<MessageResponse<Void>> deleteTourNote( @PathVariable String tourNoteId){
        tourNoteService.deleteTourNote(tourNoteId);
        return SuccessEntityResponse.OkResponse("Xóa tour note thành công", null);
    }

}
