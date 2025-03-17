package vn.edu.iuh.fit.bookingservice.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.mapper.BookingMapper;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.BookingService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager entityManager;

    private final BookingRepository bookingRepository;
    private final TourScheduleRepository tourScheduleRepository;
    private final TicketRepository ticketRepository;
    private final BookingMapper bookingMapper;

    @Override
    public List<BookingResponse> findBookingsByUserId(String userId) {
        String jpql = "SELECT b FROM Booking b WHERE b.userId = :userId";
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setParameter("userId", userId);
        List<Booking> bookings = query.getResultList();
        return bookings.stream()
                .map(booking -> new BookingResponse(
                       booking.getBookingId(),
                        booking.getStatus(),
                        booking.getTotalPrice(),
                        booking.getNote(),
                        booking.getUserFullName(),
                        booking.getUserPhone(),
                        booking.getUserEmail(),
                        booking.getUserAddress(),
                        booking.getUserId()
                ))
                .toList();
    }

//    public BookingResponseDTO createBooking(BookingRequestDTO request) {
//        TourSchedule tourSchedule = tourScheduleRepository.findById(request.getTourScheduleId())
//                .orElseThrow(() -> new RuntimeException("Tour schedule not found"));
//
//        // Tính tổng giá tiền từ danh sách vé
//        double totalPrice = request.getTickets().stream().mapToDouble(TicketRequestDTO::getPrice).sum();
//
//        // Tạo booking
//        Booking booking = Booking.builder()
//                .status(1) // Đang xử lý
//                .totalPrice(totalPrice)
//                .note(request.getNote())
//                .userFullName(request.getUserFullName())
//                .userPhone(request.getUserPhone())
//                .userEmail(request.getUserEmail())
//                .userAddress(request.getUserAddress())
//                .tourSchedule(tourSchedule)
//                .userId(request.getUserId())
//                .build();
//        bookingRepository.save(booking);
//
//        // Tạo danh sách ticket
//        List<Ticket> tickets = request.getTickets().stream().map(ticketDTO ->
//                Ticket.builder()
//                        .ticketType(TicketType.valueOf(ticketDTO.getTicketType()))
//                        .price(ticketDTO.getPrice())
//                        .status(1) // Đang xử lý
//                        .note(ticketDTO.getNote())
//                        .booking(booking)
//                        .tourSchedule(tourSchedule)
//                        .build()
//        ).collect(Collectors.toList());
//        ticketRepository.saveAll(tickets);
//
//        // Ánh xạ sang DTO Response
//        return bookingMapper.toBookingResponseDTO(booking, tickets);
//    }


}
