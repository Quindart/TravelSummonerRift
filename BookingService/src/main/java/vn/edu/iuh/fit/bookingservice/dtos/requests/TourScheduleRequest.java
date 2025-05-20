package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Data
public class TourScheduleRequest {
    private String tourScheduleId;

    @NotBlank(message = "Tên không được để trống")
    private String name;
    private String description;
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime startDate;
    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDateTime endDate;
    private double adultPrice;
    private double childPrice;
    private double babyPrice;
    @Min(value = 1,message = "Số lượng >= 1")
    private int slot;
    private String tourId;
}
