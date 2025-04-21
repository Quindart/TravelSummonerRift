package vn.edu.iuh.fit.bookingservice.mapper.impl;

import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourOverviewResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourHistory;
import vn.edu.iuh.fit.bookingservice.mapper.TourHistoryMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class TourHistoryMapperImpl implements TourHistoryMapper {

    @Override
    public TourHistoryResponse toHistoryDto(TourHistory history) {
        if (history == null) {
            return null;
        }

        TourHistoryResponse response = new TourHistoryResponse();
        response.setTourHistoryId(history.getTourHistoryId());
        response.setViewDate(history.getViewDate());
        
        // Map Tour to TourOverviewResponse
        if (history.getTour() != null) {
            response.setTour(mapToTourOverview(history.getTour()));
        }

        return response;
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

    @Override
    public List<TourHistoryResponse> toHistoryDtoList(List<TourHistory> histories) {
        if (histories == null) {
            return null;
        }

        List<TourHistoryResponse> list = new ArrayList<>(histories.size());
        for (TourHistory history : histories) {
            list.add(toHistoryDto(history));
        }

        return list;
    }
} 