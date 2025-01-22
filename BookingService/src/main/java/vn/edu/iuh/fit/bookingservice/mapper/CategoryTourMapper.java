package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import vn.edu.iuh.fit.bookingservice.dtos.requests.CategoryTourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.CategoryTourResponse;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;

@Mapper(componentModel = "spring")
public interface CategoryTourMapper {
    CategoryTour toCategoryTour(CategoryTourRequest categoryTourRequest);
    CategoryTourResponse toCategoryTourResponse(CategoryTour categoryTour);
}
