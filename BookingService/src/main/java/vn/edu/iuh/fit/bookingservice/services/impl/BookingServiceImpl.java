package vn.edu.iuh.fit.bookingservice.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.BookingMapper;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.repositories.httpclient.UserServiceClient;
import vn.edu.iuh.fit.bookingservice.services.BookingService;
import vn.edu.iuh.fit.bookingservice.services.IAuthData;

import java.time.LocalDateTime;
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
    private final UserServiceClient userServiceClient;
    private final RedisService redisService;

    @Autowired
    private IAuthData authData;

    @Override
    public BookingResponseDTO createBooking(BookingRequest request) {

        String userId = this.authData.getAuth().getUserId();
        UserResponse user = userServiceClient.getUserById(userId).getData();
        System.out.println(user);
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng");
        }

        Optional<TourSchedule> tourScheduleOpt = tourScheduleRepository.findById(request.getTourScheduleId());
        if (tourScheduleOpt.isEmpty()) {
            throw new RuntimeException("Tour Schedule không tồn tại!");
        }
        TourSchedule tourSchedule = tourScheduleOpt.get();

        if (LocalDateTime.now().isAfter(tourSchedule.getStartDate())) {
            throw new RuntimeException("Không thể đặt tour, ngày khởi hành đã qua!");
        }

        // Đếm số vé đã được đặt trước đó
        long bookedTicketsCount = ticketRepository.countByTourSchedule(tourSchedule);

        // Số lượng vé muốn đặt
        int requestedTicketsCount = request.getTickets().size();

        // Kiểm tra nếu đủ chỗ
        if (bookedTicketsCount + requestedTicketsCount > tourSchedule.getSlot()) {
            throw new RuntimeException("Không đủ chỗ để đặt vé! Số chỗ còn lại: "
                    + (tourSchedule.getSlot() - bookedTicketsCount));
        }

        Booking booking = Booking.builder()
                .status(BookingStatus.PENDING) // Đang xử lý
                .totalPrice(0) // Chưa tính tổng, sẽ cập nhật sau
                .note(request.getNote())
                .userFullName(request.getUserFullName())
                .userPhone(request.getUserPhone())
                .userEmail(request.getUserEmail())
                .userAddress(request.getUserAddress())
                .tourSchedule(tourSchedule)
                .userId(userId)
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
                    .fullName(ticketDTO.getFullName())
                    .birthDate(ticketDTO.getBirthDate())
                    .gender(ticketDTO.getGender())
                    .build();
        }).collect(Collectors.toList());

        ticketRepository.saveAll(tickets);

        // Cập nhật lại tổng tiền booking
        double totalPrice = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);
        booking.setTickets(tickets);
        BookingResponseDTO response = bookingMapper.toBookingResponseDTO(booking);
        response.setTickets(bookingMapper.toTicketResponseDTOs(tickets));

        redisService.saveBooking(booking.getBookingId());
        return response;
    }

    @Override
    public List<BookingResponseDTO> getBookingHistory() {
        String userId = this.authData.getAuth().getUserId();
        UserResponse user = userServiceClient.getUserById(userId).getData();
        System.out.println(user);
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng");
        }

        List<Booking> bookings = bookingRepository.getBookingsByUserId(userId);
        return bookings.stream()
                .map(bookingMapper::toBookingResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingMapper::toBookingResponseDTO)
                .collect(Collectors.toList());
    }


}
