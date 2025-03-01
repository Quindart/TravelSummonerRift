package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourDestinationResponse {
    private String name;
    private String description;
    private String image;
    private boolean isActive;
}
