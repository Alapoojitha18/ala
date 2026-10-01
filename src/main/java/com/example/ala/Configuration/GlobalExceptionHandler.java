package com.example.ala.Configuration;


import com.example.ala.Exception.StudentNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseGlobal<?> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseGlobal.onError(ex.getMessage());
    }
}
