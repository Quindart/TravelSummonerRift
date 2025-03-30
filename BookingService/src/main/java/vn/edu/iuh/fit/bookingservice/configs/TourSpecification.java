package vn.edu.iuh.fit.bookingservice.configs;

import org.springframework.data.jpa.domain.Specification;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class TourSpecification {
    public static Specification<Tour> filterTours(String tourName, String category, double minPrice, double maxPrice,
                                                  String city, String destination) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 1. Tìm kiếm theo tên tour (LIKE %tourName%)
            if (tourName != null && !tourName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + tourName.toLowerCase() + "%"));
            }

            // 2. Lọc theo category (chính xác)
            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            // 3. Lọc theo giá tối thiểu
            if (minPrice >= 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // 4. Lọc theo giá tối đa
            if (maxPrice >= minPrice && maxPrice >= 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // 5. Lọc theo thành phố
            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }

            // 6. Lọc theo điểm đến
            if (destination != null && !destination.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("destination"), destination));
            }

            // Trả về tất cả các điều kiện dưới dạng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}