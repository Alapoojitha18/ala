
        package com.example.ala.Student.Service;

import com.example.ala.Configuration.JWTService;
import com.example.ala.Response.ResponseGlobal;
import com.example.ala.Student.Entity.Login;
import com.example.ala.Student.Repo.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(
            LoginRepository loginRepository,
            JWTService jwtService,
            PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseGlobal<?> login(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return ResponseGlobal.onFailure(
                    "Email and password are required");
        }

        Login user = loginRepository.findByEmail(email.trim());

        if (user == null || user.getPassword() == null) {
            return ResponseGlobal.onFailure(
                    "Invalid email or password");
        }

        // Database password must be a BCrypt hash.
        if (!passwordEncoder.matches(
                password, user.getPassword())) {
            return ResponseGlobal.onFailure(
                    "Invalid email or password");
        }

        // JWTService must generate a token with subject=email
        // and a role claim.
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getName(),
                "STAFF"
        );

        return ResponseGlobal.onSuccess(
                "Login successful",
                token
        );
    }
}
