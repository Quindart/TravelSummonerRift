package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.userservice.entities.BaseEntity;

@Entity
@Table(name = "tours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tour extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tourId;
    String name;
    String description;
    double price;
    String thumbnail;
    String duration;

    @ManyToOne
    @JoinColumn(name = "category_tour_id")
    CategoryTour categoryTour;

}
