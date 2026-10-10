
        package com.example.ala.Student.Controller;

import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    // GET ALL
    @GetMapping("/getAllStudent")
    public ResponseGlobal<List<Student>> getAllStudents() {

        return studentService.getAllStudents();
    }


    // GET BY ID
    @GetMapping("/getStudentById/{id}")
    public ResponseGlobal<Student> getStudentById(
            @PathVariable Long id) {

        return studentService.getStudentById(id);
    }


    // DELETE
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseGlobal<String> deleteStudent(
            @PathVariable Long id) {

        return studentService.deleteStudent(id);
    }


    // CREATE
    @PostMapping("/createStudent")
    public ResponseGlobal<StudentDto> createStudent(
            @Valid @RequestBody StudentDto student) {

        return studentService.createStudent(student);
    }


    // UPDATE
    @PutMapping("/updateStudent/{id}")
    public ResponseGlobal<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        return studentService.updateStudent(
                id,
                student
        );
    }
}