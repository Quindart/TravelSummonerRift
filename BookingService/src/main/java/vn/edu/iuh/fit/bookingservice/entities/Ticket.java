package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.bookingservice.enums.TicketType;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String ticketId;

    double price;
    int status; // 0: Chờ thanh toán, 1: Đã thanh toán, 2: Đã sử dụng
    String note;

    @Enumerated(EnumType.STRING)
    TicketType ticketType; // PHÂN LOẠI: NGƯỜI LỚN, TRẺ EM, EM BÉ

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne
    @JoinColumn(name = "tour_schedule_id")
    TourSchedule tourSchedule;

    String fullName;
    String gender;
    LocalDate birthDate;
}
