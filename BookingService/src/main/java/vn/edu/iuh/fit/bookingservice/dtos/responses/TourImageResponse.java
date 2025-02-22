package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class TourImageResponse {
    String tourImageId;
    int orderIndex;
    String description;
}
