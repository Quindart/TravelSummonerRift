package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourNoteResponse {
    private String tourNoteId;
    private String tourId;
    private String title;
    private String content;
    private String image;
    private boolean isActive;
}
