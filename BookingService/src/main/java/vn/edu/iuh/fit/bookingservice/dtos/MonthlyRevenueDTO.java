package vn.edu.iuh.fit.bookingservice.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class MonthlyRevenueDTO {
    private int month;
    private BigDecimal totalRevenue;

    public MonthlyRevenueDTO(Number month, Number totalRevenue) {
        this.month = month.intValue();
        this.totalRevenue = new BigDecimal(totalRevenue.toString())
                .setScale(2, RoundingMode.HALF_UP);  // làm tròn 2 chữ số thập phân
    }
}
