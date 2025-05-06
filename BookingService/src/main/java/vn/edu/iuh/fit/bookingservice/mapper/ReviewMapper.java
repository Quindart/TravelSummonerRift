package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequest reviewRequest);
    ReviewResponse toReviewResponse(Review review);
}