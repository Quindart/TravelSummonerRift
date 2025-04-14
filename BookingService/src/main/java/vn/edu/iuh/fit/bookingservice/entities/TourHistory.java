package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tour_history", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tour_id"}))
public class TourHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String historyId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "view_date", nullable = false)
    private LocalDateTime viewDate;
}