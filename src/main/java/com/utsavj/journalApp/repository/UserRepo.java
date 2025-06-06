package com.utsavj.journalApp.repository;

import com.utsavj.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    public User findByUsername(String username);

    public void deleteByUsername(String username);
}
