package com.example.ala.Student.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
    @NotBlank
    private String name;
    @Min(value = 18)
    private int age;

    private double percentage;

    private String email;

    @Size(min = 2, max = 10)
    private String city;

    private String state;


    private int pincode;

    private String password;

}
