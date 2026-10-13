
package com.example.ala.Attedence.Service;

import com.example.ala.Attedence.Entity.Attendence;
import com.example.ala.Attedence.Repo.AttendanceRepository;
import com.example.ala.Student.Entity.Admin;
import com.example.ala.Student.Repo.AdminRepo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AdminRepo adminRepo;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            AdminRepo adminRepo) {
        this.attendanceRepository = attendanceRepository;
        this.adminRepo = adminRepo;
    }

    private void validateStaff(Long staffId) {
        Admin staff = adminRepo.findById(staffId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Staff not found with ID: " + staffId
                        )
                );

        if (staff.getRole() == null ||
                !"STAFF".equals(staff.getRole().name())) {
            throw new IllegalArgumentException(
                    "The supplied ID does not belong to a Staff account"
            );
        }
    }

    @Transactional
    public Attendence checkIn(Long staffId) {
        validateStaff(staffId);

        LocalDate today = LocalDate.now();

        if (attendanceRepository
                .findByStaffIdAndAttendanceDate(staffId, today)
                .isPresent()) {
            throw new IllegalStateException(
                    "Attendance already exists for today"
            );
        }

        Attendence attendance = new Attendence();
        attendance.setStaffId(staffId);
        attendance.setAttendanceDate(today);
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setStatus("PRESENT");

        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendence checkOut(Long staffId) {
        validateStaff(staffId);

        LocalDate today = LocalDate.now();

        Attendence attendance = attendanceRepository
                .findByStaffIdAndAttendanceDate(staffId, today)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Please check in before checking out"
                        )
                );

        if (attendance.getCheckOut() != null) {
            throw new IllegalStateException(
                    "You have already checked out today"
            );
        }

        attendance.setCheckOut(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    public List<Attendence> getAllAttendance() {
        return attendanceRepository
                .findAllByOrderByAttendanceDateDesc();
    }

    public List<Attendence> getAttendanceByStaffId(Long staffId) {
        validateStaff(staffId);

        return attendanceRepository
                .findByStaffIdOrderByAttendanceDateDesc(staffId);
    }
}
