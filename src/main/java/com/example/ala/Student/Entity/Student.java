package com.example.ala.Student.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @NotNull(message = "Percentage is required")
    @Min(value = 0, message = "Percentage cannot be less than 0")
    @Max(value = 100, message = "Percentage cannot be greater than 100")
    private Integer percentage;

    @Email(message = "Please enter a valid email")
    private String email;

    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Default constructor - required by JPA
    public Student() {
    }
}
