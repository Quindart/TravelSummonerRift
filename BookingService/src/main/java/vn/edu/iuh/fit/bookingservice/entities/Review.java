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


    @ElementCollection
    @CollectionTable(name = "image_urls", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "review_videos", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "video_url")
    @OrderColumn(name = "video_order")
    private List<String> videoUrls;

    @ManyToOne()
    @JoinColumn(name = "tour_schedule_id", nullable = false)
    private TourSchedule tourSchedule;
}

