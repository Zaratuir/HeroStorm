package com.herostorm.webservice.security;

import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserManager;
import org.springframework.security.core.Authentication;

public class WebSecurity {

    private UserManager userManager;

    public WebSecurity(UserManager userManager){
        this.userManager = userManager;
    }

    public boolean checkUserID(Authentication auth, String id){
        User user = userManager.getByUserName((String) auth.getPrincipal());
        return user.getId().equals(id);
    }
}
