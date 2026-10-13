package com.example.ala.STAFF.Repo;



import com.example.ala.STAFF.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository
        extends JpaRepository<Staff, Long> {

    boolean existsByEmail(String email);

    Optional<Staff> findByEmail(String email);
}

