package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String destinationId;

    String name;
    String description;
    String image;
    String address;
    String city;
    String district;
    String country;
    String cityId;
    String districtId;
}
