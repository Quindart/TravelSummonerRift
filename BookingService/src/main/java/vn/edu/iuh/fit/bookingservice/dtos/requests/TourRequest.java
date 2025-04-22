package vn.edu.iuh.fit.bookingservice.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TourRequest {
    @NotBlank(message = "Tên tour không được để trống")
    @Size(min = 3, max = 100, message = "Tên tour phải từ 3 đến 100 ký tự")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(min = 10, max = 500, message = "Mô tả phải từ 10 đến 500 ký tự")
    private String description;

    @Min(value = 0, message = "Giá tour không thể nhỏ hơn 0")
    private double price;


    @NotBlank(message = "Thumbnail không được để trống")
    private String thumbnail;

    @NotBlank(message = "Thời lượng không được để trống")
//    @Pattern(regexp = "^[0-9]+( ngày| giờ| phút)$", message = "Thời lượng phải có đơn vị (ví dụ: 3 ngày, 5 giờ, 45 phút)")
    private String duration;
    private List<TourDestinationRequest> tourDestinationRequests;
    private List<TourImageRequest> tourImageRequests;
    private String categoryId;
}
