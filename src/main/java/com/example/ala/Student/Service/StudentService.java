package com.example.ala.Student.Service;

import com.example.ala.Configuration.ResponseGlobal;

import com.example.ala.Enum.RoleEnum;
import com.example.ala.Student.DTO.AdminDTO;
import com.example.ala.Student.DTO.StudentDto;
import com.example.ala.Student.Entity.Address;
import com.example.ala.Student.Entity.Admin;
import com.example.ala.Student.Entity.Login;

import com.example.ala.Student.Entity.Student;
import com.example.ala.Student.Repo.AdminRepo;
import com.example.ala.Student.Repo.LoginRepository;
import com.example.ala.Student.Repo.StudentRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepos studentRepos;
    private final LoginRepository loginRepository;
    private final AdminRepo adminRepo;


    // =========================
    // CREATE STUDENT
    // =========================

    public ResponseGlobal<StudentDto> createStudent(
            StudentDto student) {

        if (student.getEmail() == null ||
                student.getEmail().trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Email is required."
            );
        }


        Student existingStudent =
                studentRepos.findByEmail(
                        student.getEmail()
                );


        if (existingStudent != null) {

            return ResponseGlobal.onFailure(
                    "This email is already being used by another student."
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

        login.setEmail(student.getEmail());
        login.setPassword(student.getPassword());
        login.setName(student.getName());


        // IMPORTANT
        login.setRole(RoleEnum.ADMIN.name());
        loginRepository.save(login);


        return ResponseGlobal.onSuccess(
                "Student and login details created successfully.",
                student
        );
    }


    // =========================
    // GET STUDENT BY ID
    // =========================

    public ResponseGlobal<Student> getStudentById(
            Long id) {

        if (id == null) {

            return ResponseGlobal.onFailure(
                    "Student ID is required."
            );
        }


        Optional<Student> optionalStudent =
                studentRepos.findById(id);


        if (optionalStudent.isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Student not found with id: " + id
            );
        }


        Student student =
                optionalStudent.get();


        return ResponseGlobal.onSuccess(
                "Student retrieved successfully.",
                student
        );
    }


    // =========================
    // GET ALL STUDENTS
    // =========================

    public ResponseGlobal<List<Student>> getAllStudents() {

        List<Student> students =
                studentRepos.findAll();


        if (students.isEmpty()) {

            return ResponseGlobal.onFailure(
                    "No students found."
            );
        }


        return ResponseGlobal.onSuccess(
                "Students retrieved successfully.",
                students
        );
    }


    // =========================
    // UPDATE STUDENT
    // =========================

    public ResponseGlobal<Student> updateStudent(
            Long id,
            Student student) {


        if (id == null) {

            return ResponseGlobal.onFailure(
                    "Student ID is required."
            );
        }


        Optional<Student> optionalStudent =
                studentRepos.findById(id);


        if (optionalStudent.isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Student not found with id: " + id
            );
        }


        Student existingStudent =
                optionalStudent.get();


        // Check email
        if (student.getEmail() != null &&
                !student.getEmail().trim().isEmpty()) {


            Student existingEmail =
                    studentRepos.findByEmail(
                            student.getEmail()
                    );


            // Email belongs to another student
            if (existingEmail != null &&
                    !existingEmail.getId().equals(id)) {

                return ResponseGlobal.onFailure(
                        "This email is already being used by another student."
                );
            }


            existingStudent.setEmail(
                    student.getEmail()
            );
        }


        existingStudent.setName(
                student.getName()
        );

        existingStudent.setAge(
                student.getAge()
        );

        existingStudent.setPercentage(
                student.getPercentage()
        );


        Student updatedStudent =
                studentRepos.save(existingStudent);


        return ResponseGlobal.onSuccess(
                "Student has been updated successfully.",
                updatedStudent
        );
    }


    // =========================
    // DELETE STUDENT
    // =========================

    public ResponseGlobal<String> deleteStudent(
            Long id) {


        if (id == null) {

            return ResponseGlobal.onFailure(
                    "Student ID is required."
            );
        }


        if (!studentRepos.existsById(id)) {

            return ResponseGlobal.onFailure(
                    "Student with id " + id +
                            " does not exist."
            );
        }


        studentRepos.deleteById(id);


        return ResponseGlobal.onSuccess(
                "Student deleted successfully.",
                "Student with id " + id +
                        " has been deleted."
        );
    }


    // =========================
    // CREATE ADMIN
    // =========================

    public ResponseGlobal<AdminDTO> createAdmin(
            AdminDTO adminDTO) {


        if (adminDTO.getEmail() == null ||
                adminDTO.getEmail().trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Email is required for Admin creation."
            );
        }


        if (adminDTO.getPassword() == null ||
                adminDTO.getPassword().trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Password is required for Admin creation."
            );
        }


        if (adminDTO.getSecretKey() == null ||
                adminDTO.getSecretKey().trim().isEmpty()) {

            return ResponseGlobal.onFailure(
                    "Secret Key is required for Admin creation."
            );
        }


        if (!adminDTO.getSecretKey().equals(secretKey())) {

            return ResponseGlobal.onFailure(
                    "Invalid Secret Key provided."
            );
        }


        boolean emailExists =
                loginRepository.existsByEmail(
                        adminDTO.getEmail()
                );


        if (emailExists) {

            return ResponseGlobal.onFailure(
                    "This email is already being used."
            );
        }


        // Create Admin
        Admin admin = new Admin();

        admin.setEmail(adminDTO.getEmail());
        admin.setName(adminDTO.getName());

        adminRepo.save(admin);


        // Create Login
        Login login = new Login();

        login.setEmail(adminDTO.getEmail());
        login.setPassword(adminDTO.getPassword());
        login.setName(adminDTO.getName());

        login.setRole(
                RoleEnum.ADMIN.name()
        );

        loginRepository.save(login);


        return ResponseGlobal.onSuccess(
                "Admin has been created successfully.",
                adminDTO
        );
    }


    private String secretKey() {

        return "kanna123456789012345678901234567890";
    }
}
