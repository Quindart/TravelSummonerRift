package vn.edu.iuh.fit.bookingservice.dtos.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;


@Data
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    String bookingId;
    BookingStatus status;
    double totalPrice;
    String note;
    String userFullName;
    String userPhone;
    String userEmail;
    String userAddress;
    String userId;

    public BookingResponse(String bookingId, BookingStatus status, double totalPrice, String note, String userFullName, String userPhone, String userEmail, String userAddress, String userId) {
        this.bookingId = bookingId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.note = note;
        this.userFullName = userFullName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userId = userId;
    }
}
