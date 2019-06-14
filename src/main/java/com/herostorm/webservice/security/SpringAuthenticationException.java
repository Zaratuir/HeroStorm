package com.herostorm.webservice.security;

import org.springframework.security.core.AuthenticationException;

public class SpringAuthenticationException extends AuthenticationException {
    public SpringAuthenticationException(String message){
        super(message);
    }
}
