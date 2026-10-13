package com.example.ala.STAFF.Controller;



import com.example.ala.STAFF.DTO.StaffDTO;

import com.example.ala.STAFF.Entity.Staff;
import com.example.ala.STAFF.Service.StaffService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStaff(
            @Valid @RequestBody StaffDTO dto) {
        try {
            Staff staff = staffService.createStaff(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Staff profile created successfully",
                            "data", staff
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/all")
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(staffService.getStaffById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStaff(
            @PathVariable Long id,
            @Valid @RequestBody StaffDTO dto) {
        try {
            return ResponseEntity.ok(
                    staffService.updateStaff(id, dto)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        try {
            staffService.deleteStaff(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Staff deleted successfully");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }
}

