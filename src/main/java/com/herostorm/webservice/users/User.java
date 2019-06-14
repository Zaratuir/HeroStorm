package com.herostorm.webservice.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.herostorm.webservice.decks.Deck;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Document(collection="Users")
public class User {


    public static enum Role {
        ADMIN,USER
    }

    @Id
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String userName;
    private ArrayList<Role> userRoles;
    private long passwordExpireDT;
    @DBRef
    private List<Deck> decks;
    private long userExpiredDT = -1;
    private long lockDT = -1;
    private boolean enabled = true;

    public User(){
        userRoles = new ArrayList<Role>();
        userRoles.add(Role.USER);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Role> getUserRoles() {
        return userRoles;
    }

    public ArrayList<String> getUserRolesAsString(){
        ArrayList<String> returnRoles = new ArrayList<String>();
        for(Role i : this.userRoles){
            returnRoles.add(i.toString());

        }
        return returnRoles;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUserRoles(ArrayList<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public long getPasswordExpireDT() {
        return passwordExpireDT;
    }

    public void setPasswordExpireDT(long passwordExpireDT) {
        this.passwordExpireDT = passwordExpireDT;
    }

    public long getUserExpiredDT() {
        return userExpiredDT;
    }

    public void setUserExpiredDT(long userExpiredDT) {
        this.userExpiredDT = userExpiredDT;
    }

    public long getLockDT() {
        return lockDT;
    }

    public void setLockDT(long lockDT) {
        this.lockDT = lockDT;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
