
        package com.example.ala.Student.Controller;

import com.example.ala.Student.DTO.AdminDTO;
import com.example.ala.Student.DTO.AdminLoginDTO;
import com.example.ala.Student.Service.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create a STAFF account
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createStaff(
            @RequestBody AdminDTO adminDTO) {

        String result = adminService.createAdmin(adminDTO);

        if ("Staff account created successfully".equals(result)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", result
            ));
        }

        HttpStatus status =
                "Admin email already exists".equals(result)
                        ? HttpStatus.CONFLICT
                        : "Invalid admin secret key".equals(result)
                        ? HttpStatus.FORBIDDEN
                        : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(Map.of(
                "success", false,
                "message", result
        ));
    }

    // Admin or STAFF login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> adminLogin(
            @RequestBody AdminLoginDTO loginDTO) {

        try {
            return ResponseEntity.ok(
                    adminService.adminLogin(loginDTO)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }
}
