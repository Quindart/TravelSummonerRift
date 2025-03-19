package vn.edu.iuh.fit.userservice.infra.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.edu.iuh.fit.userservice.infra.booking.client.IBookingClient;
import vn.edu.iuh.fit.userservice.infra.booking.dto.BookingOfUserResponse;
import vn.edu.iuh.fit.userservice.infra.booking.dto.BookingResponse;

import java.util.List;

@Service
public class BookingOfUserService {

    @Autowired
    private IBookingClient bookingClient;

    public BookingOfUserResponse getBookingOfUser(String userId) {

        ResponseEntity<List<BookingResponse>> booksResponse = bookingClient.getBookingsOfUser();

        BookingOfUserResponse bookingOfUserRes = new BookingOfUserResponse();
        bookingOfUserRes.setUserId(userId);
        bookingOfUserRes.setBookings(booksResponse.getBody());

        return bookingOfUserRes;
    }
}
