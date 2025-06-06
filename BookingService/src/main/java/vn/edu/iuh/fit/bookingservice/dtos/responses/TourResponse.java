package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

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
    private List<TourScheduleResponse> tourScheduleResponses;
    private boolean isActive;
    private CategoryTour categoryTour;
}
