
        package com.example.ala.Student.Repo;

import com.example.ala.Student.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository
        extends JpaRepository<Login, Long> {

    Login findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
