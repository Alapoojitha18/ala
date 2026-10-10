package com.example.ala.Student.Entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "admin")

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;


}
