package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.userservice.entities.BaseEntity;

@Entity
@Table(name = "tour_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourNote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tourNoteId;

    String title;
    String content;
    String image;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;
}
