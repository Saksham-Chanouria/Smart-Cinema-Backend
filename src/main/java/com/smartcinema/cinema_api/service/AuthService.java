package com.smartcinema.cinema_api.service;

import com.smartcinema.cinema_api.dto.LoginRequest;
import com.smartcinema.cinema_api.dto.LoginResponse;
import com.smartcinema.cinema_api.dto.SignupResponse;
import com.smartcinema.cinema_api.entities.User;
import org.springframework.stereotype.Service;


public interface AuthService {
    SignupResponse saveUser(User theUser);
    LoginResponse checkUser(LoginRequest loginRequest);
}
