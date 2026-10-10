package com.example.ala.Student.Repo;
import com.example.ala.Student.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminRepo extends JpaRepository<Admin, Long> {
        Admin findByEmail(String email);
    }

