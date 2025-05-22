package vn.edu.iuh.fit.bookingservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.configs.VNPayConfig;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.BookingMapper;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.impl.RedisService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayService {

    private final VNPayConfig vnPayConfig;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final TourScheduleRepository tourScheduleRepository;
    private final TicketRepository ticketRepository;
    private final RedisService redisService;
    private final BookingCleanupService bookingCleanupService;

    public String createPaymentUrl(String bookingId, long amount, String bankCode, String language) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        // amount phải nhân với 100 vì VNPay yêu cầu đơn vị là cent
        long vnp_Amount = amount * 100;
//        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);  // Mã giao dịch duy nhất
        String vnp_TxnRef =bookingId;
        String vnp_IpAddr = "127.0.0.1";  // Lấy địa chỉ IP của người dùng

        String vnp_TmnCode = vnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        // Kiểm tra ngôn ngữ, nếu không có thì mặc định là 'vn'
        if (language != null && !language.isEmpty()) {
            vnp_Params.put("vnp_Locale", language);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Tạo thời gian tạo và hết hạn của giao dịch
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar cld = Calendar.getInstance(vietnamTimeZone);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(vietnamTimeZone);
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);  // Thời gian hết hạn giao dịch là 15 phút sau
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Xử lý để tạo URL
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                // Tạo chuỗi hashData cho chữ ký
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                // Tạo chuỗi query cho URL
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // Tạo Secure Hash
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public void handleSuccessedPayment(String txnRef) {
        Booking booking = bookingRepository.findById(txnRef)
                .orElseThrow(() -> new NotFoundException("Booking không tồn tại!"));



        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);

        if(redisService.isBookingExist(txnRef)){
            redisService.deleteBooking(txnRef);
        }
    }

    @Scheduled(fixedDelay = 60000*5)
    public void checkExpiredBookings() {
        List<Booking> bookings = bookingRepository.findAllByStatus(BookingStatus.PENDING);

        for (Booking booking : bookings) {
            long ttl = redisService.getTTL(booking.getBookingId());
            if (ttl == -2) {
                booking.setStatus(BookingStatus.EXPIRED);
                bookingRepository.save(booking);
                bookingCleanupService.handleFailedPayment(booking.getBookingId());
            }
        }
    }

}

