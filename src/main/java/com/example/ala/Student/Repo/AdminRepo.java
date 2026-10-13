package com.example.ala.Student.Repo;

import com.example.ala.Enum.RoleEnum;
import com.example.ala.Student.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRole(RoleEnum role);
}