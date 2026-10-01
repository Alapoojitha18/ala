package com.example.ala.Student.Controller;


import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
// i have a method inside my Controller whcih is getAll student and it haa mapping /getAllStudent
//then my api endpoint it will become as a localhost:8124/students/getAllStudent
//Lombok
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getAllStudent")
    public List<Student> getAllStudents (){
       return studentService.getAllStudents();
    }

    @GetMapping("/getStudentById/{id}")
   public Student getStudentById(@PathVariable Long id ){

        return studentService.getStudentById(id);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id){
       return studentService.deleteStudent(id);
    }

    @PostMapping("/createStudent")
    public ResponseGlobal<StudentDto> createStudent(@Valid @RequestBody StudentDto student){
        return studentService.createStudent(student);
    }


    @PutMapping("/updateStudent/{id}")
    public ResponseGlobal<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
        return studentService.updateStudent(id,student);
    }



}
