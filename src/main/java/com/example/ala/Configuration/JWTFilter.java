
        package com.example.ala.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        return path.equals("/admin/create")
                || path.equals("/admin/createAdmin")
                || path.equals("/admin/login")
                || path.equals("/students/createStudent")
                || path.equals("/login/StudentLogin")
                || path.equals("/test");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // Continue without a token; SecurityConfig decides access.
        if (header == null || header.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!header.startsWith("Bearer ")) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authorization header must use Bearer token"
            );
            return;
        }

        String token = header.substring(7).trim();

        try {
            Claims claims = jwtService.getClaims(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            if (email == null || email.isBlank()
                    || role == null
                    || !(role.equals("ADMIN") || role.equals("STAFF"))) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT claims"
                );
                return;
            }

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority("ROLE_" + role)
                            )
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT token"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
