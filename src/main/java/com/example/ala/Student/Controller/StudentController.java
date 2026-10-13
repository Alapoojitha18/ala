package com.example.ala.Student.Controller;
import com.example.ala.Response.ResponseGlobal;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }


    // CREATE STUDENT
    @PostMapping("/create")
    public ResponseGlobal<StudentDto> createStudent(
            @RequestBody StudentDto student) {

        return service.createStudent(student);
    }
//    get all students
    @GetMapping("/getAllStudent")
    public ResponseGlobal<List<Student>> getAllStudent() {

        List<Student> students =
                service.getAllStudents();

        return ResponseGlobal.onSuccess(
                "Students fetched successfully",
                students
        );
    }


    // GET STUDENT BY ID
    @GetMapping("/{id}")
    public ResponseGlobal<Student> getStudentById(
            @PathVariable Long id) {

        Student student =
                service.getStudentById(id);

        return ResponseGlobal.onSuccess(
                "Student fetched successfully",
                student
        );
    }


    // UPDATE STUDENT
    @PutMapping("/{id}")
    public ResponseGlobal<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return service.updateStudent(id, student);
    }


    // DELETE STUDENT
    @DeleteMapping("/{id}")
    public ResponseGlobal<String> deleteStudent(
            @PathVariable Long id) {

        String message =
                service.deleteStudent(id);

        return ResponseGlobal.onSuccess(
                message,
                null
        );
    }
}