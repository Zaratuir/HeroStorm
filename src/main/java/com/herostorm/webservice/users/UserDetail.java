package com.herostorm.webservice.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class UserDetail implements UserDetails {
    private User user;

    public UserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for(User.Role role : user.getUserRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }

    public boolean hasAuthority(User.Role authority){
        SecurityContext sc = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = sc.getAuthentication().getAuthorities();
        for(GrantedAuthority auth : authorities){
            if(auth.getAuthority().equals("ROLE_"+authority)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        long expireLong = user.getUserExpiredDT();
        if(expireLong == -1){
            return false;
        }
        Date expireDate = new Date(expireLong);
        boolean isExpired = expireDate.before(new Date());
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        long lockLong = user.getLockDT();
        if(lockLong == -1){
            return false;
        }
        Date lockDT = new Date(lockLong);
        boolean isLocked = lockDT.after(new Date());
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        Date passwordExpireDT = new Date(user.getPasswordExpireDT());
        boolean expiredPassword = passwordExpireDT.before(new Date());
        return !expiredPassword;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}