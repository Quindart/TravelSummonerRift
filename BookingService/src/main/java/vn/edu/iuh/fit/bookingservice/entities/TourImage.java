package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tourImageId;

    int orderIndex;
    String description;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;
}
