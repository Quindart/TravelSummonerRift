package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Data
public class TourScheduleRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;
    private String description;
    @NotBlank(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime startDate;
    @NotBlank(message = "Ngày kết thúc không được để trống")
    private LocalDateTime endDate;
    private double adultPrice;
    private double childPrice;
    private double babyPrice;
    @NotBlank(message = "Số lượng không được để trống")
    private int slot;
    private String tourId;
}
