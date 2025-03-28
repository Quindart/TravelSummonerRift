package vn.edu.iuh.fit.bookingservice.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tourScheduleId;

    String name;
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;

    double adultPrice;
    double childPrice;
    double babyPrice;
    int slot;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;
}
