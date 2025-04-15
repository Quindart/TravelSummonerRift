package vn.edu.iuh.fit.bookingservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tour_favorites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tour_id"}))
public class TourFavorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tourFavoriteId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
     private Tour tour;

}
