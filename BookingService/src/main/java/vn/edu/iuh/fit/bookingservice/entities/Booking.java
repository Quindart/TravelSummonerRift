package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;

import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String bookingId;

    @Enumerated(EnumType.STRING)
    BookingStatus status;
    double totalPrice;
    String note;

    String userFullName;
    String userPhone;
    String userEmail;
    String userAddress;

    @ManyToOne
    @JoinColumn(name = "tourSchedule_id")
    TourSchedule tourSchedule;

    String userId; // ID của user đặt tour

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Ticket> tickets;

    public Booking(String bookingId) {
        this.bookingId = bookingId;
    }
}
