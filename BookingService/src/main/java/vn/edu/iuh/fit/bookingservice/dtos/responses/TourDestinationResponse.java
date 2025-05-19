package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.entities.Destination;

@Data
public class TourDestinationResponse {
    private String name;
    private String description;
    private String image;
    private boolean isActive;
    private int orderIndex;
    private DestinationResponse destination;
}
