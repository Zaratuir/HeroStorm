package com.herostorm.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UserManager {

    @Autowired
    UserRepo userRepo;

    @RequestMapping(path="/Users/{id}")
    public User getUser(@PathVariable("id") String id) throws ResponseStatusException {
        Optional<User> userOption = userRepo.findById(id);
        if(!userOption.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User With ID: " + id + " Not Found.");
        }
        return userOption.get();
    }
}
