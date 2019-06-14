package com.herostorm.webservice.users;

import com.herostorm.webservice.decks.Deck;
import com.herostorm.webservice.utilities.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(path="/Users")
public class UserManager {

    UserRepo userRepo;
    UserPasswordManager passwordManager;
    DateTimeUtil dateTimeUtil;

    @Inject
    public UserManager(UserRepo userRepo, UserPasswordManager passwordManager, DateTimeUtil dateTimeUtil){
        this.userRepo = userRepo;
        this.passwordManager = passwordManager;
        this.dateTimeUtil = dateTimeUtil;
    }

    @PostMapping(path="/Validate", headers = "Authorization")
    public User validateUser(@RequestHeader("Authorization") String authHeader, HttpServletResponse response) throws ResponseStatusException{
        SecurityContext sc = SecurityContextHolder.getContext();
        String username = (String) sc.getAuthentication().getPrincipal();
        String password = (String) sc.getAuthentication().getCredentials();
        User validUser = userRepo.getByUserName(username);
        if(validUser == null){
            response.setHeader("Authorization","");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Or Password Invalid");
        }
        if(!passwordManager.testPassword(validUser,password)){
            response.setHeader("Authorization","");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Or Password Invalid");
        }
        return validUser;
    }

    //@PreAuthorize("#id = authentication.id or hasRole('ROLE_ADMIN')")
    @GetMapping(path="/{id}")
    public User getUser(@PathVariable("id") String id) throws ResponseStatusException {
        Optional<User> userOption = userRepo.findById(id);
        if(userOption.isPresent()){
            return userOption.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User With ID: " + id + " Not Found.");
        }
    }

    @PostMapping(path="/{id}/updateCredentials")
    public User updateUserCredentials(@PathVariable("id") String id, @RequestBody UserCredentials userCredentials) throws ResponseStatusException {
        SecurityContext sc = SecurityContextHolder.getContext();
        Optional<User> currUserOption = userRepo.findById(id);
        if(!currUserOption.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with ID: " + id + " Not Found.");
        }
        User currUser = currUserOption.get();
        if(!new UserDetail(currUser).hasAuthority(User.Role.ADMIN) && (userCredentials.getCurrPassword() == null || !passwordManager.testPassword(currUser, userCredentials.getCurrPassword()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current Password Incorrect");
        }
        String newUserName = userCredentials.getUserName();
        if(newUserName != null){
            if(userRepo.getByUserName(newUserName) != null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + newUserName + " Already Taken");
            }
            currUser.setUserName(userCredentials.getUserName());
        }
        currUser.setPassword(passwordManager.hashPassword(userCredentials.getPassword()));
        currUser.setPasswordExpireDT(this.dateTimeUtil.get90Days());
        currUser = userRepo.save(currUser);
        return currUser;
    }

    @PostMapping(path="/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User user) throws ResponseStatusException {
        Optional<User> currUserOption = userRepo.findById(id);
        if (!currUserOption.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with ID: " + id + " Not Found.");
        }
        User currUser = currUserOption.get();

        if(user.getUserName() != null){
            if(userRepo.getByUserName(user.getUserName()) != null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserName: " + user.getUserName() + " Already Taken");
            }
            currUser.setUserName(user.getUserName());
        }

        if(user.getEmail() != null){
            currUser.setEmail(user.getEmail());
        }

        if(user.getId() != null && user.getId() != id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update a user other than yourself.");
        }

        if(user.getFirstName() != null){
            currUser.setFirstName(user.getFirstName());
        }

        if(user.getLastName() != null) {
            currUser.setLastName(user.getLastName());
        }
        currUser = userRepo.save(currUser);
        return currUser;
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        if(user.getId() != null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Unable to POST to Users with ID in body. Please use /Users/{id} to update a user.");
        }
        ArrayList<String> missingFields = new ArrayList<String>();
        if(user.getEmail() == null) {
            missingFields.add("email");
        }
        if(user.getFirstName() == null ){
            missingFields.add("firstName");
        }
        if(user.getLastName() == null ){
            missingFields.add("lastName");
        }
        if( user.getUserName() == null ){
            missingFields.add("userName");
        }
        if( user.getUserRoles() == null ){
            missingFields.add("userType");
        }
        if(user.getPassword() == null){
            missingFields.add("password");
        }
        if(!missingFields.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing Required Fields: " + String.join(",", missingFields));
        }
        if(userRepo.getByUserName(user.getUserName()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username " + user.getUserName() + " Already Exists.");
        }
        user.setDecks(new ArrayList<Deck>());
        user.setPassword(user.getPassword());
        user.setPasswordExpireDT(this.dateTimeUtil.get90Days());
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    public User getByUserName(String username){
        return userRepo.getByUserName(username);
    }
}
