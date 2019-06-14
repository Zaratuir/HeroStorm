package com.herostorm.webservice.security;

import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserDetail;
import com.herostorm.webservice.users.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class AuthenticateUserService implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userManager.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetail(user);
    }

    // Used by JWT Authentication Filter
    public UserDetails loadUserById(String id) {
        User user = userManager.getUser(id);
        return new UserDetail(user);
    }
}