package com.utsavj.journalApp.controller;
import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers =  userService.getAllUsers();
        if (allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("create-admin")
    public void createAdmin(@RequestBody User user){
        userService.createAdmin(user);
    }
}
