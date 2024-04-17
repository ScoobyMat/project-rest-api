package com.project.service;

import com.project.auth.AuthRequest;
import com.project.auth.AuthResponse;
import com.project.model.Student;

public interface AuthService {
	void register(Student student);
	AuthResponse authenticate(String email, String password);
	AuthResponse authenticate(AuthRequest request);
}