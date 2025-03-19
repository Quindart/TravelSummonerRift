package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TicketRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TicketResponse;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.TicketType;

import java.util.Arrays;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TicketMapper {
//    @Mapping(source = "ticketType", target = "ticketType", qualifiedByName = "mapTicketTypeName")
//    @Mapping(source = "booking", target = "bookingId", qualifiedByName = "mapBookingId")
//    @Mapping(source = "tourSchedule", target = "tourScheduleName", qualifiedByName = "mapTourScheduleName")
//    @Mapping(source = "tourSchedule", target = "tourScheduleDesc", qualifiedByName = "mapTourScheduleDesc")
//    @Mapping(source = "tourSchedule.startDate", target = "tourScheduleStartTime")
//    @Mapping(source = "tourSchedule.endDate", target = "tourScheduleEndTime")
//    TicketResponse toDTO(Ticket ticket);
//
//    @Mapping(target = "ticketType", source = "ticketType", qualifiedByName = "mapTicketTypeEntity")
//    @Mapping(target = "booking", source = "bookingId", qualifiedByName = "mapBookingEntity")
//    @Mapping(target = "tourSchedule", source = ".", qualifiedByName = "mapTourScheduleEntity")
//    Ticket toEntity(TicketRequest request);
//
//    @Named("mapTicketTypeName")
//    static String mapTicketTypeName(TicketType ticketType) {
//        return Optional.ofNullable(ticketType).map(TicketType::getName).orElse(null);
//    }
//
//    @Named("mapBookingId")
//    static String mapBookingId(Booking booking) {
//        return Optional.ofNullable(booking).map(Booking::getBookingId).orElse(null);
//    }
//
//    @Named("mapTourScheduleName")
//    static String mapTourScheduleName(TourSchedule tourSchedule) {
//        return Optional.ofNullable(tourSchedule).map(TourSchedule::getName).orElse(null);
//    }
//
//    @Named("mapTourScheduleDesc")
//    static String mapTourScheduleDesc(TourSchedule tourSchedule) {
//        return Optional.ofNullable(tourSchedule).map(TourSchedule::getDescription).orElse(null);
//    }
//
//    @Named("mapTicketTypeEntity")
//    static TicketType mapTicketTypeEntity(String name) {
//        return Arrays.stream(TicketType.values())
//                .filter(type -> type.name().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Named("mapBookingEntity")
//    static Booking mapBookingEntity(String bookingId) {
//        return bookingId == null ? null : new Booking(bookingId);
//    }
//
//    @Named("mapTourScheduleEntity")
//    static TourSchedule mapTourScheduleEntity(TicketRequest request) {
//        if (request == null) return null;
//        TourSchedule tourSchedule = new TourSchedule();
//        tourSchedule.setName(request.getTourScheduleName());
//        tourSchedule.setDescription(request.getTourScheduleDesc());
//        tourSchedule.setStartDate(request.getTourScheduleStartTime());
//        tourSchedule.setEndDate(request.getTourScheduleEndTime());
//
//        return (tourSchedule.getName() == null && tourSchedule.getDescription() == null &&
//                tourSchedule.getStartDate() == null && tourSchedule.getEndDate() == null) ? null : tourSchedule;
//    }

    TicketDTO toDTO(Ticket ticket);
    Ticket toEntity(TicketDTO ticketDTO);
}
