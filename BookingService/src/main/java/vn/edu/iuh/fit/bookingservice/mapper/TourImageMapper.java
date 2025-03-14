package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;

@Mapper(componentModel = "spring")
public interface TourImageMapper {
    @Mappings({
            @Mapping(target = "isActive", constant = "true"),
            @Mapping(target = "imageUrl", ignore = true)
    })
    TourImage toTourImage(TourImageRequest tourImageRequest);

    @Mappings({
            @Mapping(target = "tourId", source = "tour.tourId")
    })
    TourImageResponse toTourImageResponse(TourImage tourImage);
}


