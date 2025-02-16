package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.TourNoteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourNoteResponse;

import java.util.List;

public interface TourNoteService {
    List<TourNoteResponse> getAllTourNote();
    TourNoteResponse getTourNoteById(String tourNoteId);
    TourNoteResponse createTourNote(TourNoteRequest tourNoteRequest);
    TourNoteResponse updateTourNote(String tourNoteId, TourNoteRequest tourNoteRequest);
    void deleteTourNote(String tourNoteId);
}
