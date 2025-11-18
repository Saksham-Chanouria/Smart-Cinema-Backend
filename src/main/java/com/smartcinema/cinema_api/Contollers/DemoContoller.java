package com.smartcinema.cinema_api.Contollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoContoller {
    @GetMapping("/hello")
    public String demo(){
        return "Hello world";
    }
}
