package com.example.ala.Controller;

import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.DTO.AdminDTO;
import com.example.ala.Student.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;

    @PostMapping("/createAdmin")
    public ResponseGlobal<?> createAdmin(
            @RequestBody AdminDTO adminDTO) {

        return studentService.createAdmin(adminDTO);
    }
}