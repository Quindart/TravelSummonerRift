package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourOverviewResponse {
    private String tourId;
    private String name;
    private double price;
    private String thumbnail;
    private String duration;
    private float rating;
}
