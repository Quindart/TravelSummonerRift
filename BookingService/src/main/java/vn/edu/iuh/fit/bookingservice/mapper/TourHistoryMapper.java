package vn.edu.iuh.fit.bookingservice.mapper;

import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourHistory;

import java.util.List;

public interface TourHistoryMapper {
    TourHistoryResponse toHistoryDto(TourHistory history);
    List<TourHistoryResponse> toHistoryDtoList(List<TourHistory> histories);

}
