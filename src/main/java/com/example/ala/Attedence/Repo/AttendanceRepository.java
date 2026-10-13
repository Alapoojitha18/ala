
package com.example.ala.Attedence.Repo;

import com.example.ala.Attedence.Entity.Attendence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendence, Long> {

    Optional<Attendence> findByStaffIdAndAttendanceDate(
            Long staffId,
            LocalDate attendanceDate
    );

    List<Attendence> findByStaffIdOrderByAttendanceDateDesc(
            Long staffId
    );

    List<Attendence> findAllByOrderByAttendanceDateDesc();
}
