package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_destinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tourDestinationId;

    String name;
    String description;
    String image;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    Destination destination;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;


}
