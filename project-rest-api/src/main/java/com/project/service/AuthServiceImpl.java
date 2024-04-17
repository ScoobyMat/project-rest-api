package com.project.service;

import com.project.auth.AuthRequest;
import com.project.auth.AuthResponse;
import io.micrometer.common.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.model.CustomUserDetails;
import com.project.model.Role;
import com.project.model.Student;



@Service
public class AuthServiceImpl implements AuthService {
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(StudentService studentService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.USER);
        studentService.createStudent(student);
    }

    @Override
    public AuthResponse authenticate(String email, String password) {
        return null;
    }

    /*public AuthResponse authenticate(@NonNull String email, @NonNull String password) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = studentService
                .searchByEmail(email)
                .map(s -> new CustomUserDetails(s))
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User '%s' not found!", email)));
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }*/
    public AuthResponse authenticate(AuthRequest request) {
        return authenticate(request.getEmail(), request.getPassword());
    }


}