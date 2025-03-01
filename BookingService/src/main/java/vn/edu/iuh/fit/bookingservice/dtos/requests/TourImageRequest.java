package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TourImageRequest {
    private String tourId;
    private String description;
    private int orderIndex;
}
