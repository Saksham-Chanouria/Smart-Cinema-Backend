package com.smartcinema.cinema_api.service;


import com.smartcinema.cinema_api.dao.AuthDAO;
import com.smartcinema.cinema_api.dto.SignupResponse;
import com.smartcinema.cinema_api.entities.Role;
import com.smartcinema.cinema_api.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService{
    private AuthDAO authDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(AuthDAO theAuthDAO){
        authDAO = theAuthDAO;
    }

    @Transactional
    public SignupResponse saveUser(User theUser){

        SignupResponse signupResponse = new SignupResponse();

        if(theUser == null){
            signupResponse.setSuccess(false);
            signupResponse.setStatusCode(400);
            signupResponse.setMessage("Invalid request: empty body");
            signupResponse.setTimeStamp(System.currentTimeMillis());
            return signupResponse;
        }

        if (theUser.getEmail() == null || theUser.getEmail().isBlank() ||
                theUser.getPassword() == null || theUser.getPassword().isBlank() ||
                theUser.getFullName() == null || theUser.getFullName().isBlank()) {

            signupResponse.setSuccess(false);
            signupResponse.setMessage("Missing required fields");
            signupResponse.setStatusCode(400);
            signupResponse.setTimeStamp(System.currentTimeMillis());
            return signupResponse;
        }

        if(authDAO.existsByEmail(theUser.getEmail())){
            signupResponse.setSuccess(false);
            signupResponse.setStatusCode(409);
            signupResponse.setMessage("Email already exists!");
            signupResponse.setTimeStamp(System.currentTimeMillis());
        }
        else{
            try{
                // Convert password to hashCode
                String hashed = bCryptPasswordEncoder.encode(theUser.getPassword());
                theUser.setPassword(hashed);

                theUser.setCreatedAt(LocalDateTime.now());
                theUser.setUpdatedAt(LocalDateTime.now());

                User savedUser = authDAO.save(theUser);

                Role savedRole = new Role("User");
                savedRole.setUser(savedUser);

                savedUser.getRoles().add(savedRole);

                authDAO.save(savedUser);
                signupResponse.setSuccess(true);
                signupResponse.setStatusCode(201);
                signupResponse.setMessage("User registered successfully!");
            }
            catch (Exception e){
                throw e;
            }
        }

        return signupResponse;

    }
}
