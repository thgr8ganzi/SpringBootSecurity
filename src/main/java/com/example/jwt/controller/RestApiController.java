package com.example.jwt.controller;

import com.example.jwt.model.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/token")
    public String token(){
        return "token";
    }
}
