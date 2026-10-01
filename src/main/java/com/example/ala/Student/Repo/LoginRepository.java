package com.example.ala.Student.Repo;


import com.example.ala.Student.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByEmail(String email);

    Login findByEmailAndPassword(String email, String password);
}
