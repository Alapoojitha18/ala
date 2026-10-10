
        package com.example.ala.Student.Repo;

import com.example.ala.Student.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository
        extends JpaRepository<Login, Long> {

    Login findByEmail(String email);

    Login findByEmailAndPassword(
            String email,
            String password
    );

    boolean existsByEmail(String email);
}
