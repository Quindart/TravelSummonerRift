package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourResponse {
    private String tourId;
    private String name;
    private String description;
    private double price;
    private String thumbnail;
    private String duration;
    private boolean isActive;
}
