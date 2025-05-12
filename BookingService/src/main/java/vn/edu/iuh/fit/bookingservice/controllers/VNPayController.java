package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.configs.VNPayConfig;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.errors.BadRequestException;
import vn.edu.iuh.fit.bookingservice.services.VNPayService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {

    @Autowired
    private VNPayService paymentService;

    @Autowired
    private VNPayConfig vnPayConfig;

    @Autowired
    private VNPayService vnPayService;


    @PostMapping("/create-payment-url")
    public MessageResponse<String> createPaymentUrl(
            @RequestParam String bookingId,
            @RequestParam long amount,
            @RequestParam(required = false) String bankCode,
            @RequestParam(required = false) String language) throws UnsupportedEncodingException {

            String paymentUrl = paymentService.createPaymentUrl(bookingId, amount, bankCode, language);
            return MessageResponse.<String>builder()
                    .success(true)
                    .message("Payment Url")
                    .data(paymentUrl)
                    .build();

    }

    @GetMapping("/ipn")
    public MessageResponse<Void> handleIPN(@RequestParam Map<String, String> allParams) {
        // Clone params để xử lý (vì cần xóa bớt các field hash)
        Map<String, String> fields = new HashMap<>();

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String fieldName = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String fieldValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
            }
        }

        // Lấy chữ ký do VNPay gửi về
        String vnp_SecureHash = allParams.get("vnp_SecureHash");

        // Xóa các trường không dùng để tạo chữ ký
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        // Tạo lại chữ ký từ dữ liệu nhận được
        String signValue = vnPayConfig.hashAllFields(fields);


        if (!signValue.equals(vnp_SecureHash)) {
            throw new BadRequestException("Sai chữ ký!");
        }

        // Giao dịch hợp lệ -> xử lý
        String vnp_TxnRef = allParams.get("vnp_TxnRef");
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");


        if ("00".equals(vnp_ResponseCode)) {

            vnPayService.handleSuccessedPayment(vnp_TxnRef);

            return MessageResponse.<Void>builder()
                    .statusCode(200)
                    .success(true)
                    .message("Thanh toán thành công")
                    .build();
        } else {
            System.out.println("Fail");
            return MessageResponse.<Void>builder()
                    .statusCode(200)
                    .success(true)
                    .message("Thanh toán không thành công")
                    .build();
        }


    }

}
