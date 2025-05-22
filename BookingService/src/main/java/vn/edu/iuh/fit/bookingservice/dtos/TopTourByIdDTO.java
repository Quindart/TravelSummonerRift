package vn.edu.iuh.fit.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopTourByIdDTO {
    private String tourId;
    private Long totalBooking;
}
