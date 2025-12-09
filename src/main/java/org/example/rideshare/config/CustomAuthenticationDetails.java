package org.example.rideshare.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class CustomAuthenticationDetails extends WebAuthenticationDetails {

    private final String userId;
    private final String role;

    public CustomAuthenticationDetails(HttpServletRequest request, String userId, String role) {
        super(request);
        this.userId = userId;
        this.role = role;
    }
}
