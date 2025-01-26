package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

@Data
public class CategoryTourResponse {
    private String categoryTourId;
    private String name;
    private String description;
    private String image;
    private boolean isActive;
}
