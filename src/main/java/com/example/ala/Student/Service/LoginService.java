
        package com.example.ala.Student.Service;

import com.example.ala.Configuration.JWTService;
import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.Entity.Login;
import com.example.ala.Student.Repo.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final JWTService jwtService;


    public ResponseGlobal<String> login(
            String email,
            String password) {


        // Check email
        if (email == null || email.trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Email is required."
            );
        }


        // Check password
        if (password == null || password.trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Password is required."
            );
        }


        // Find user by email
        Login user =
                loginRepository.findByEmail(email);


        if (user == null) {

            return ResponseGlobal.onFailure(
                    "User not found with email: " + email
            );
        }


        // Check email and password
        Login validUser =
                loginRepository.findByEmailAndPassword(
                        email,
                        password
                );


        if (validUser == null) {

            return ResponseGlobal.onFailure(
                    "Invalid password."
            );
        }


        // Get role
        String role = validUser.getRole();


        // Generate JWT
        String jwt =
                jwtService.generateToken(
                        validUser.getEmail(),
                        validUser.getName(),
                        role
                );


        return ResponseGlobal.onSuccess(
                "Login successful.",
                jwt
        );
    }
}
