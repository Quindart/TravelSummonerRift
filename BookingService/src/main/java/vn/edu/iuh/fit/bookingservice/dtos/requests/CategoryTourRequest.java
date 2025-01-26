package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryTourRequest {
    @NotBlank(message = "Tên Category Tour hong được để trống")
    private String name;
    private String description;
    private String image;
}
