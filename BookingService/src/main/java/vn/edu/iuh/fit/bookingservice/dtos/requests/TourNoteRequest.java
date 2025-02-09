package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TourNoteRequest {
    @NotBlank(message = "Tour ID không được để trống")
    private String tourId;
    @NotBlank(message = "Tiêu đề Tour Note không được để trống")
    private String title;
    private String content;
    private String image;
}
