package vn.edu.iuh.fit.userservice.dtos.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String otp;
    private String newPassword;
}
