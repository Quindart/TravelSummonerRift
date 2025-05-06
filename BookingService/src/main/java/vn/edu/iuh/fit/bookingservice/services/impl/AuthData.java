package vn.edu.iuh.fit.bookingservice.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;

public class AuthData {
    public PrincipalAuthentication getAuthData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof PrincipalAuthentication) {
            PrincipalAuthentication principal = (PrincipalAuthentication) authentication.getPrincipal();
           return principal;
        }

        return null; // Nếu không tìm thấy, trả về null
    }
}
