package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;
import vn.edu.iuh.fit.bookingservice.services.IAuthData;
@Service
public class AuthData implements IAuthData {
    public PrincipalAuthentication getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof PrincipalAuthentication) {
            PrincipalAuthentication principal = (PrincipalAuthentication) authentication.getPrincipal();
           return principal;
        }
        return null;
    }


}
