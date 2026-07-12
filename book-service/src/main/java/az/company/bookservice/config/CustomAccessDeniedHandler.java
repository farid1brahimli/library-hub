package az.company.bookservice.config;

import az.company.bookservice.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(SC_FORBIDDEN);
        response.setContentType("application/json");

        var responseBody = ErrorResponse.builder()
                .status(FORBIDDEN.value())
                .error("You do not have permission to access this resource")
                .message("Access Denied")
                .timestamp(LocalDateTime.now())
                .build();

        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}