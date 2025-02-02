package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourNoteRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourNoteResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;
import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourNoteMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourNoteRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.services.TourNoteService;

import java.util.List;

@Service
public class TourNoteServiceImpl implements TourNoteService {
    @Autowired
    private TourNoteRepository tourNoteRepository;

    @Autowired
    private TourNoteMapper tourNoteMapper;

    // Other required imports

    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<TourNoteResponse> getAllTourNote() {
        List<TourNoteResponse> tourNoteResponses = tourNoteRepository.findAll()
                .stream()
                .map(tourNoteMapper::toTourNoteResponse)
                .collect(java.util.stream.Collectors.toList());
        return tourNoteResponses;
    }

    @Override
    public TourNoteResponse getTourNoteById(String tourNoteId) {
        TourNote tourNote = tourNoteRepository.findById(tourNoteId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour note"));

        return tourNoteMapper.toTourNoteResponse(tourNote);
    }

    @Override
    public TourNoteResponse createTourNote(TourNoteRequest tourNoteRequest) {
        TourNote tourNote = tourNoteMapper.toTourNote(tourNoteRequest);
        tourNote = tourNoteRepository.save(tourNote);
        if (tourNote == null) {
            throw new InternalServerErrorException("Tạo tour note thất bại");
        }
        return tourNoteMapper.toTourNoteResponse(tourNote);
    }

    @Override
    public TourNoteResponse updateTourNote(String tourNoteId, TourNoteRequest tourNoteRequest) {
        TourNote tourNote = tourNoteRepository.findById(tourNoteId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour note"));

        tourNote.setTitle(tourNoteRequest.getTitle());
        tourNote.setContent(tourNoteRequest.getContent());

        Tour tour = tourRepository.findById(tourNoteRequest.getTourId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour"));


        tourNote.setTour(tour);
        tourNote = tourNoteRepository.save(tourNote);
        if (tourNote == null) {
            throw new vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException("Cập nhật tour note thất bại: không tim thấy tour");
        }
        return tourNoteMapper.toTourNoteResponse(tourNote);
    }

    @Override
    public void deleteTourNote(String tourNoteId) {
        TourNote tourNote = tourNoteRepository.findById(tourNoteId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour note"));

        tourNote.setActive(false);

        tourNoteRepository.save(tourNote);
    }
}
