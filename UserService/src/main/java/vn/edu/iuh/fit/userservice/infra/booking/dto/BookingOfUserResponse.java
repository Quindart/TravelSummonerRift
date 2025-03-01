package vn.edu.iuh.fit.userservice.infra.booking.dto;

import java.util.List;

public class BookingOfUserResponse {
    private String userId;
    private String username;
    private List<BookingResponse> bookings;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookingResponse> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingResponse> bookings) {
        this.bookings = bookings;
    }
}
