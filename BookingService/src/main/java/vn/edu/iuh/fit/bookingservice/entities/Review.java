package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewId;

    private LocalDateTime reviewDate;

    private String userId;

    private float rating;

    String username;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ElementCollection()
    @CollectionTable(
            name = "review_files",
            joinColumns = @JoinColumn(name = "review_id")
    )
    @Column(name = "file_url")
    @OrderColumn(name = "file_order")
    private List<String> files;

    @ManyToOne()
    @JoinColumn(name = "tour_schedule_id", nullable = false)
    private TourSchedule tourSchedule;
}

