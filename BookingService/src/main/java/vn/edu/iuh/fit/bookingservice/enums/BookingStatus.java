package vn.edu.iuh.fit.bookingservice.enums;

public enum BookingStatus {
    PENDING,      // Chờ thanh toán
    PAID,         // Đã thanh toán thành công
    FAILED,       // Thanh toán thất bại
    CANCELLED,    // Đã bị hủy
    EXPIRED,      // Hết hạn thanh toán
    REFUNDED      // Đã hoàn tiền
}