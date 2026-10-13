
package com.example.ala.Student.Service;

import com.example.ala.Enum.RoleEnum;
import com.example.ala.Student.DTO.AdminDTO;
import com.example.ala.Student.DTO.AdminLoginDTO;
import com.example.ala.Student.Entity.Admin;
import com.example.ala.Student.Repo.AdminRepo;
import com.example.ala.Configuration.JWTService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Value("${app.admin.secret-key}")
    private String adminSecretKey;

    public AdminService(
            AdminRepo adminRepo,
            PasswordEncoder passwordEncoder,
            JWTService jwtService) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String createAdmin(AdminDTO dto) {

        if (dto.getSecretKey() == null ||
                !adminSecretKey.equals(dto.getSecretKey())) {
            return "Invalid admin secret key";
        }

        if (adminRepo.existsByEmail(dto.getEmail())) {
            return "Admin email already exists";
        }

        if (dto.getName() == null || dto.getName().isBlank()
                || dto.getEmail() == null || dto.getEmail().isBlank()
                || dto.getPassword() == null
                || dto.getPassword().length() < 8) {
            return "Name, email and a password of at least 8 characters are required";
        }

        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        // This endpoint creates a STAFF account.
        admin.setRole(RoleEnum.STAFF);

        adminRepo.save(admin);

        return "Staff account created successfully";
    }

    public Map<String, Object> adminLogin(
            AdminLoginDTO loginDTO) {

        Admin admin = adminRepo.findByEmail(
                loginDTO.getEmail());

        if (admin == null ||
                !passwordEncoder.matches(
                        loginDTO.getPassword(),
                        admin.getPassword())) {
            throw new IllegalArgumentException(
                    "Invalid email or password");
        }

        if (admin.getRole() != RoleEnum.ADMIN &&
                admin.getRole() != RoleEnum.STAFF) {
            throw new IllegalArgumentException(
                    "Role not authorized");
        }

        // Change this call if your JWTService uses a different
        // token-generation method name or parameter order.
        String token = jwtService.generateToken(
                admin.getName(),
                admin.getEmail(),
                admin.getRole().name());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("name", admin.getName());
        response.put("email", admin.getEmail());
        response.put("role", admin.getRole().name());

        return response;
    }
}
