package vn.edu.iuh.fit.bookingservice.mapper.impl;


import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourFavoriteResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourOverviewResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;
import vn.edu.iuh.fit.bookingservice.mapper.TourFavoriteMapper;

import java.util.ArrayList;
import java.util.List;

@Component("tourFavoriteMapperImplA")
public class TourFavoriteMapperImpl implements TourFavoriteMapper {

    @Override
    public TourFavoriteResponse toFavoriteDto(TourFavorite entity) {
        if (entity == null) {
            return null;
        }

        TourFavoriteResponse response = new TourFavoriteResponse();
        response.setTourFavoriteId(entity.getTourFavoriteId());

        // Map Tour to TourOverviewResponse
        if (entity.getTour() != null) {
            response.setTour(mapToTourOverview(entity.getTour()));
        }

        return response;
    }

    @Override
    public List<TourFavoriteResponse> toFavoriteDtoList(List<TourFavorite> entities) {
        if (entities == null) {
            return null;
        }

        List<TourFavoriteResponse> list = new ArrayList<>(entities.size());
        for (TourFavorite entity : entities) {
            list.add(toFavoriteDto(entity));
        }

        return list;
    }

    private TourOverviewResponse mapToTourOverview(Tour tour) {
        if (tour == null) {
            return null;
        }

        TourOverviewResponse overview = new TourOverviewResponse();
        overview.setTourId(tour.getTourId());
        overview.setName(tour.getName());
        overview.setPrice(tour.getPrice());
        overview.setThumbnail(tour.getThumbnail());
        overview.setDuration(tour.getDuration());

        return overview;
    }
}