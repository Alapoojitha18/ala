
        package com.example.ala.Student.Controller;

import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;


    @PostMapping("/StudentLogin")
    public ResponseGlobal<String> login(
            @RequestParam String email,
            @RequestParam String password) {

        return loginService.login(
                email,
                password
        );
    }
}
