package com.utsavj.journalApp.service;

import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }


    public Optional<User> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteUserById(ObjectId id){
        userRepo.deleteById(id);
    }

    public void deleteUserByUsername(String username){
        userRepo.deleteByUsername(username);
    }
}
