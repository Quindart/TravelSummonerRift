package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.iuh.fit.bookingservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.FileReviewDto;
import vn.edu.iuh.fit.bookingservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.bookingservice.entities.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequest reviewRequest);
    @Mapping(source = "tourSchedule.tourScheduleId",target ="tourScheduleId" )
    @Mapping(source = "files",target = "files",qualifiedByName = "mapFiles")
    ReviewResponse toReviewResponse(Review review);

    @Named("mapFiles")
    default List<FileReviewDto> mapFiles(List<String> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        return files.stream()
                .map(file -> new FileReviewDto(file, files.indexOf(file)))
                .collect(Collectors.toList());
    }
}