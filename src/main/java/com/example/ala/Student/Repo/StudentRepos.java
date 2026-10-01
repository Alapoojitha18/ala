package com.example.ala.Student.Repo;


import com.example.ala.Student.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepos extends JpaRepository<Student, Long> {

    List<Student> findByName(String name);
    Student findByEmail(String email);

    //it iwll genrate the quesry as "select * from students where name = ?"
    List<Student> findByAgeGreaterThan(int age);

}
//what repo will provide us??
/*
save()
findById()
findAll()
deleteById()
existsById() //boolean
count() //long

 */