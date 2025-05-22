package vn.edu.iuh.fit.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BookingCategoryStatDTO {
    private String categoryName;
    private int count;

    public BookingCategoryStatDTO(String categoryName, Number count) {
        this.categoryName = categoryName;
        this.count = count.intValue();
    }
}