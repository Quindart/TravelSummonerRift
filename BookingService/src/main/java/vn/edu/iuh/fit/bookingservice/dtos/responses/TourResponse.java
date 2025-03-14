package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;

import java.util.List;

@Data
public class TourResponse {
    private String tourId;
    private String name;
    private String description;
    private double price;
    private String thumbnail;
    private String duration;
    private List<TourDestinationResponse> tourDestinationResponses;
    private List<TourImageResponse> tourImageResponses;
    private boolean isActive;
}
