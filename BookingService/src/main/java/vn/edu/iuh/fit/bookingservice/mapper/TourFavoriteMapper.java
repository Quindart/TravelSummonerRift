package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourFavoriteMapper {
    TourFavoriteResponse toFavoriteDto(TourFavorite entity);
    List<TourFavoriteResponse> toFavoriteDtoList(List<TourFavorite> entities);
}
