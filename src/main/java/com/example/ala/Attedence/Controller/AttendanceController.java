
package com.example.ala.Attedence.Controller;

import com.example.ala.Attedence.Entity.Attendence;
import com.example.ala.Attedence.Service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in/{staffId}")
    public ResponseEntity<?> checkIn(@PathVariable Long staffId) {
        try {
            return ResponseEntity.ok(
                    attendanceService.checkIn(staffId)
            );
        } catch (IllegalArgumentException |
                 IllegalStateException ex) {
            return errorResponse(ex.getMessage());
        }
    }

    @PostMapping("/check-out/{staffId}")
    public ResponseEntity<?> checkOut(@PathVariable Long staffId) {
        try {
            return ResponseEntity.ok(
                    attendanceService.checkOut(staffId)
            );
        } catch (IllegalArgumentException |
                 IllegalStateException ex) {
            return errorResponse(ex.getMessage());
        }
    }

    @GetMapping
    public List<Attendence> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getAttendanceByStaffId(
            @PathVariable Long staffId) {
        try {
            return ResponseEntity.ok(
                    attendanceService.getAttendanceByStaffId(staffId)
            );
        } catch (IllegalArgumentException ex) {
            return errorResponse(ex.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> errorResponse(
            String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);

        return ResponseEntity.badRequest().body(response);
    }
}
