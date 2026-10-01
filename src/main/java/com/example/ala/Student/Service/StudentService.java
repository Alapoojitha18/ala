package com.example.ala.Student.Service;

import com.example.ala.Configuration.ResponseGlobal;
import com.example.ala.Exception.StudentNotFoundException;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Address;
import com.example.ala.Student.Entity.Login;
import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Repo.LoginRepository;
import com.example.ala.Student.Repo.StudentRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepos studentRepos;
    private final LoginRepository loginRepository;


    // CREATE STUDENT
    public ResponseGlobal<StudentDto> createStudent(StudentDto student) {

        // Email validation
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            return ResponseGlobal.onError("Email is required.");
        }

        // Check email uniqueness
        Student existingStudent =
                studentRepos.findByEmail(student.getEmail());

        System.out.println(
                "Requested Email: " + student.getEmail()
                        + " | Existing Student: " + existingStudent
        );

        if (existingStudent != null) {
            return ResponseGlobal.onFailure(
                    "This student email is already attached to another student."
            );
        }

        // Create Student
        Student newStudent = new Student();

        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        newStudent.setPercentage(student.getPercentage());
        newStudent.setEmail(student.getEmail());
        newStudent.setStatus(true);

        // Create Address
        Address address = new Address();

        address.setCity(student.getCity());
        address.setState(student.getState());
        address.setPincode(student.getPincode());

        newStudent.setAddress(address);

        // Save Student
        studentRepos.save(newStudent);


        // Create Login
        Login login = new Login();

        login.setPassword(student.getPassword());
        login.setEmail(student.getEmail());
        login.setName(student.getName());

        loginRepository.save(login);


        return ResponseGlobal.onSuccess(
                "Student and login details have been created successfully.",
                student
        );
    }


    // GET STUDENT BY ID
    public Student getStudentById(Long id) {

        return studentRepos.findById(id)
                .orElseThrow(
                        () -> new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );
    }


    // GET ALL STUDENTS
    public List<Student> getAllStudents() {

        return studentRepos.findAll();
    }


    // UPDATE STUDENT
    public ResponseGlobal<Student> updateStudent(
            Long id,
            Student student
    ) {

        if (id == null) {
            return ResponseGlobal.onError(
                    "Student id is required."
            );
        }

        // Find existing student
        Student existingStudent = studentRepos.findById(id)
                .orElseThrow(
                        () -> new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );


        // Check email only if email is being changed
        if (student.getEmail() != null &&
                !student.getEmail().equals(existingStudent.getEmail())) {

            Student existingEmail =
                    studentRepos.findByEmail(student.getEmail());

            if (existingEmail != null) {
                return ResponseGlobal.onFailure(
                        "This email is already attached to another student."
                );
            }

            existingStudent.setEmail(student.getEmail());
        }


        // Update fields
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setPercentage(student.getPercentage());


        // Save updated student
        Student updatedStudent =
                studentRepos.save(existingStudent);


        return ResponseGlobal.onSuccess(
                "Student has been updated successfully.",
                updatedStudent
        );
    }


    // DELETE STUDENT
    public String deleteStudent(Long id) {

        if (!studentRepos.existsById(id)) {

            return "Student with id " + id
                    + " does not exist.";
        }

        studentRepos.deleteById(id);

        return "Student with id " + id
                + " has been deleted successfully.";
    }
}