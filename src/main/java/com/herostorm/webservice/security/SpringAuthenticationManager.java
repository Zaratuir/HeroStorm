package com.herostorm.webservice.security;

import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserManager;
import com.herostorm.webservice.users.UserPasswordManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.inject.Inject;

public class SpringAuthenticationManager implements AuthenticationManager {

    @Inject
    private UserManager userManager;

    @Inject
    private UserPasswordManager userPasswordManager;

    public SpringAuthenticationManager(){

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userManager.getByUserName((String) authentication.getPrincipal());
        if(user == null){
            authentication.setAuthenticated(false);
        }
        return authentication;
    }
}