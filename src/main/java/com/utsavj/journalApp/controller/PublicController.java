package com.utsavj.journalApp.controller;

import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("health-check")
    public String check() {
        return "All good";
    }

    @PostMapping("create-user")
    public void createNewUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("USER"));
        userService.saveUser(user);
    }
}
