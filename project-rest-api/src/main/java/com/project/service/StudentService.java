package com.project.service;

import com.project.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudent(Integer studentId);
    Student createStudent(Student student);
    Student updateStudent(Student student, Integer studentId);
    void deleteStudent(Integer studentId);
    Page<Student> getStudenci(Pageable pageable);
    Page<Student> searchByNazwa(String nazwisko, Pageable pageable);
    Optional<Student> searchByEmail(String email);
}
