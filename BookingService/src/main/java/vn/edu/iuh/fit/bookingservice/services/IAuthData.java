package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;


public interface IAuthData {
     PrincipalAuthentication getAuth();
}
