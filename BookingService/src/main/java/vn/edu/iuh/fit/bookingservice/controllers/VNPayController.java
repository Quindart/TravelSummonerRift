package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.services.VNPayService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {

    @Autowired
    private VNPayService paymentService;


    @PostMapping("/create-payment-url")
    public ResponseEntity<String> createPaymentUrl(
                                                    @RequestParam String bookingId,
                                                    @RequestParam long amount,
                                                   @RequestParam(required = false) String bankCode,
                                                   @RequestParam(required = false) String language) {
        try {
            String paymentUrl = paymentService.createPaymentUrl(bookingId,amount, bankCode, language);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo URL thanh toán: " + e.getMessage());
        }
    }

    @GetMapping("/ipn")
    public String iPN(
            @RequestParam String vnp_TxnRef,
            @RequestParam String vnp_Amount,
            @RequestParam String vnp_SecureHash) {

        System.out.println("Đã gọi vào đây");
        // Kiểm tra các tham số nhận được
        System.out.println("vnp_TxnRef: " + vnp_TxnRef);
        System.out.println("vnp_Amount: " + vnp_Amount);
        System.out.println("vnp_SecureHash: " + vnp_SecureHash);

        // Xử lý thêm nếu cần

        return "Test INP - Đã nhận thông báo";
    }

    @GetMapping("/result")
    public ResponseEntity<?> paymentResult(@RequestParam Map<String, String> params) {
        String transactionStatus = params.get("vnp_TransactionStatus");

        if ("00".equals(transactionStatus)) {
            // Thanh toán thành công
            return ResponseEntity.ok("Giao dịch thành công!");
        } else {
            // Thanh toán thất bại
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giao dịch không thành công");
        }
    }
}
