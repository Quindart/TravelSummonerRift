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
import vn.edu.iuh.fit.bookingservice.entities.Ticket;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.mapper.BookingMapper;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.BookingService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public BookingResponseDTO createBooking(BookingRequest request) {
        // Lấy thông tin TourSchedule
        Optional<TourSchedule> tourScheduleOpt = tourScheduleRepository.findById(request.getTourScheduleId());
        if (tourScheduleOpt.isEmpty()) {
            throw new RuntimeException("Tour Schedule không tồn tại!");
        }
        TourSchedule tourSchedule = tourScheduleOpt.get();

        // Tạo Booking entity
        Booking booking = Booking.builder()
                .status(1) // Đang xử lý
                .totalPrice(0) // Chưa tính tổng, sẽ cập nhật sau
                .note(request.getNote())
                .userFullName(request.getUserFullName())
                .userPhone(request.getUserPhone())
                .userEmail(request.getUserEmail())
                .userAddress(request.getUserAddress())
                .tourSchedule(tourSchedule)
                .userId(request.getUserId())
                .build();

        booking = bookingRepository.save(booking);

        // Tạo danh sách Ticket và lưu xuống DB
        Booking finalBooking = booking;
        List<Ticket> tickets = request.getTickets().stream().map(ticketDTO -> {
            double ticketPrice = switch (ticketDTO.getTicketType()) {
                case ADULT -> tourSchedule.getAdultPrice();
                case CHILD -> tourSchedule.getChildPrice();
                case BABY -> tourSchedule.getBabyPrice();
            };

            return Ticket.builder()
                    .ticketType(ticketDTO.getTicketType())
                    .price(ticketPrice) // Giá từ TourSchedule
                    .status(1) // Mặc định active
                    .note(ticketDTO.getNote())
                    .booking(finalBooking)
                    .tourSchedule(tourSchedule)
                    .build();
        }).collect(Collectors.toList());

        ticketRepository.saveAll(tickets);

        // Cập nhật lại tổng tiền booking
        double totalPrice = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);

        // Trả về DTO
        BookingResponseDTO response = bookingMapper.toBookingResponseDTO(booking, tickets);
        response.setTickets(bookingMapper.toTicketResponseDTOs(tickets));

        return response;
    }

}
