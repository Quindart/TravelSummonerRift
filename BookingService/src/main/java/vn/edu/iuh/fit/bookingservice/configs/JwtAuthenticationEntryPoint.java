package vn.edu.iuh.fit.bookingservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import vn.edu.iuh.fit.bookingservice.exception.ErrorMessageDto;
import vn.edu.iuh.fit.bookingservice.exception.errors.UnauthorizedException;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        UnauthorizedException unauthorizedException = new UnauthorizedException("Yêu cầu đăng nhập");
        ErrorMessageDto errorMessageDto =
                new ErrorMessageDto(HttpServletResponse.SC_UNAUTHORIZED, unauthorizedException.getMessage(), false);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(errorMessageDto));
        response.flushBuffer();
    }
}
