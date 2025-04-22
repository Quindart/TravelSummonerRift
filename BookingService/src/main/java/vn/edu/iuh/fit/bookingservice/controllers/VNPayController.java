package vn.edu.iuh.fit.bookingservice.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.services.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/create-payment-url")
    public ResponseEntity<String> createPaymentUrl(@RequestParam long amount,
                                                   @RequestParam(required = false) String bankCode,
                                                   @RequestParam(required = false) String language) {
        try {
            // Gọi service để tạo URL thanh toán VNPay
            String paymentUrl = paymentService.createPaymentUrl(amount, bankCode, language);
            return ResponseEntity.ok(paymentUrl);  // Trả về URL thanh toán
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo URL thanh toán: " + e.getMessage());  // Xử lý lỗi
        }
    }
}
