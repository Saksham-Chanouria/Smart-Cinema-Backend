package com.smartcinema.cinema_api.service;


import com.smartcinema.cinema_api.dao.AuthDAO;
import com.smartcinema.cinema_api.dto.LoginRequest;
import com.smartcinema.cinema_api.dto.LoginResponse;
import com.smartcinema.cinema_api.dto.SignupResponse;
import com.smartcinema.cinema_api.entities.Role;
import com.smartcinema.cinema_api.entities.User;
import com.smartcinema.cinema_api.exception.InvalidCredentialsException;
import com.smartcinema.cinema_api.exception.InvalidRequestException;
import com.smartcinema.cinema_api.security.JWT;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Clean the email
        String email = theUser.getEmail().trim().toLowerCase();

        if(!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new InvalidRequestException("Invalid email format");
        }
        theUser.setEmail(email);

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

                Role savedRole = new Role("User");
                savedRole.setUser(theUser);

                theUser.getRoles().add(savedRole);

                authDAO.save(theUser);
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

    public LoginResponse checkUser(LoginRequest loginRequest){
        // Clean email
        String email = loginRequest.getEmail().trim().toLowerCase();
        if(!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new InvalidRequestException("Invalid email format");
        }

        if(loginRequest.getPassword()==null || loginRequest.getPassword().isEmpty()){
            throw new InvalidRequestException("Password cannot be empty");
        }

        User theUser = authDAO.findByEmail(email);

        if(theUser == null){
            throw new InvalidCredentialsException("Invalid Username or Password!");
        }

        String fullName = theUser.getFullName();
        String firstName = fullName.split(" ")[0];

        String token = new JWT().generateToken(theUser);

        if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(),theUser.getPassword())){
            System.out.println(loginRequest.getPassword());
            System.out.println(theUser.getPassword());
            throw new InvalidCredentialsException("Invalid Username or Password!");
        }
        return new LoginResponse(200, "Login Successful",true,System.currentTimeMillis(),token,firstName);
    }
}
