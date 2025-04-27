package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.services.VNPayService;

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
}
