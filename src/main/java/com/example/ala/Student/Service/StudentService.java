package com.example.ala.Student.Service;
import com.example.ala.Exception.StudentNotFoundException;
import com.example.ala.Response.ResponseGlobal;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Address;
import com.example.ala.Student.Entity.Login;
import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Repo.LoginRepository;
import com.example.ala.Student.Repo.StudentRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepos studentRepos;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE STUDENT
    @Transactional
    public ResponseGlobal<StudentDto> createStudent(StudentDto student) {

        if (student == null) {
            return ResponseGlobal.onError(
                    "Student details are required."
            );
        }

        if (student.getEmail() == null
                || student.getEmail().isBlank()) {
            return ResponseGlobal.onError(
                    "Email is required."
            );
        }

        if (student.getPassword() == null
                || student.getPassword().isBlank()) {
            return ResponseGlobal.onError(
                    "Password is required."
            );
        }

        String email = normalizeEmail(student.getEmail());

        // Check duplicate email in Student table
        if (studentRepos.findByEmail(email) != null) {
            return ResponseGlobal.onFailure(
                    "This student email is already attached to another student."
            );
        }

        // Check duplicate email in Login table
        if (loginRepository.existsByEmail(email)) {
            return ResponseGlobal.onFailure(
                    "This email is already registered for login."
            );
        }

        // Create Student
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        newStudent.setPercentage(student.getPercentage());
        newStudent.setEmail(email);
        newStudent.setStatus(true);

        // Create Address
        Address address = new Address();
        address.setCity(student.getCity());
        address.setState(student.getState());
        address.setPincode(student.getPincode());

        newStudent.setAddress(address);

        studentRepos.save(newStudent);

        // Create Login account
        Login login = new Login();
        login.setName(student.getName());
        login.setEmail(email);
        login.setPassword(
                passwordEncoder.encode(student.getPassword())
        );

        loginRepository.save(login);

        return ResponseGlobal.onSuccess(
                "Student and login details have been created successfully.",
                student
        );
    }

    // GET STUDENT BY ID
    public Student getStudentById(Long id) {

        return studentRepos.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );
    }

    // GET ALL STUDENTS
    public List<Student> getAllStudents() {
        return studentRepos.findAll();
    }

    // UPDATE STUDENT
    @Transactional
    public ResponseGlobal<Student> updateStudent(
            Long id,
            Student student) {

        if (id == null) {
            return ResponseGlobal.onFailure(
                    "Student ID cannot be null."
            );
        }

        if (student == null) {
            return ResponseGlobal.onError(
                    "Student details are required."
            );
        }

        Student existingStudent =
                studentRepos.findById(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student not found with id: " + id
                                )
                        );

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setPercentage(student.getPercentage());

        // Validate email if supplied
        if (student.getEmail() != null
                && !student.getEmail().isBlank()) {

            String email = normalizeEmail(student.getEmail());

            Student existingEmail =
                    studentRepos.findByEmail(email);

            if (existingEmail != null
                    && !existingEmail.getId().equals(id)) {
                return ResponseGlobal.onFailure(
                        "This email is already attached to another student."
                );
            }

            existingStudent.setEmail(email);
        }

        return ResponseGlobal.onSuccess(
                "Student has been updated successfully.",
                studentRepos.save(existingStudent)
        );
    }

    // DELETE STUDENT AND RELATED LOGIN
    @Transactional
    public String deleteStudent(Long id) {

        if (id == null) {
            return "Student ID cannot be null.";
        }

        Student student = studentRepos.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        String email = student.getEmail();

        // Delete related Login record first
        if (email != null && !email.isBlank()) {
            loginRepository.deleteByEmail(
                    normalizeEmail(email)
            );
        }

        // Delete Student record
        studentRepos.delete(student);

        return "Student and related login account deleted successfully.";
    }

    // NORMALIZE EMAIL
    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}