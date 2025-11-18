package com.smartcinema.cinema_api.Contollers;

import com.smartcinema.cinema_api.dto.SignupResponse;
import com.smartcinema.cinema_api.entities.User;
import com.smartcinema.cinema_api.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService theAuthService){
        authService = theAuthService;
    }

    @PostMapping("/signup")
    public SignupResponse registerUser(@RequestBody User theUser){
        return authService.saveUser(theUser);
    }

}
