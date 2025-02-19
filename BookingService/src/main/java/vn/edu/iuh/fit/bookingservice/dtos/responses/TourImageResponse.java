package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourImageResponse {
    private String tourImageId;
    private String tourId;
    private String imageUrl;
    private String description;
    private int orderIndex;
    private boolean isActive;

}
