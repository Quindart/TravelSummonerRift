package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.dtos.responses.DestinationResponse;

@Data
public class TourDestinationRequest {
    private String name;
    private String description;
    private int orderIndex;
    private String tourId;
    private String destinationId;
//    private String image;
}
