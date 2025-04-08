package vn.edu.iuh.fit.bookingservice.configs;

import org.springframework.data.jpa.domain.Specification;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class TourSpecification {
        public static Specification<Tour> filterTours(String tourName, String category,
                                                      Double minPrice, Double maxPrice,
                                                      String city, String destination) {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (tourName != null && !tourName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + tourName.toLowerCase() + "%"));
                }

                if (category != null && !category.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("category"), category));
                }

                if (minPrice != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
                }

                if (maxPrice != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
                }

                if (city != null && !city.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("city"), city));
                }

                if (destination != null && !destination.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("destination"), destination));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }

}