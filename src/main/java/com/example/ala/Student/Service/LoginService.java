package com.example.ala.Student.Service;


import com.example.ala.Configuration.JWTService;
import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.Entity.Login;
import com.example.ala.Student.Repo.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginRepository loginRepository;
    private JWTService jwtService;
    public LoginService(LoginRepository loginRepository , JWTService jwtService) {
        this.loginRepository = loginRepository;
        this.jwtService = jwtService;
    }

    public ResponseGlobal<?> login(String email, String password ){

        if (email == null || password == null) {
           return ResponseGlobal.onFailure("Email and password must not be null");
        }
        Login userExistsOrNot = loginRepository.findByEmail(email);
        if(userExistsOrNot == null){
            return ResponseGlobal.onFailure("User not found with email: " + email);
        }
        Login checkPassword = loginRepository.findByEmailAndPassword(email, password);
        if (checkPassword == null) {
            return ResponseGlobal.onFailure("Invalid password for email: " + email);
        }
        String jwt  = jwtService.generateToken(email, userExistsOrNot.getName());
        return ResponseGlobal.onSuccess("Login successful", jwt);

    }
}
