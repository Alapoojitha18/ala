package com.example.ala.Student.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
//data has setter , getter , noargs constructor , all args constructor , toString , equals and hashcode methods.
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //id is the primary key for the student entity and will be generated automatically.

    private String name;
    private int age;
    private double percentage;
    private String email;

    private boolean status ; //false and true


    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Address address;

    //hard Delete , Soft delete


    //Lazy will actually generate the address only when we call the getAddress method and it will not generate the address when we call the getStudent method.
    //eager will actually generate all of the address when we call the getStudent method and it will not generate the address when we call the getAddress method.
}

