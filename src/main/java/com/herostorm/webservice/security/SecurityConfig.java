package com.herostorm.webservice.security;

import com.herostorm.webservice.users.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.inject.Inject;

@Configuration
public class SecurityConfig {

    @Bean("AuthenticationManager")
    @Inject
    public AuthenticationManager authenticationManager(AuthenticateUserService userSecurityService) {
        SpringAuthenticationManager authenticationManager =
                new SpringAuthenticationManager();

        return authenticationManager;
    }

    @Bean
    @Inject
    public SecurityConfig securityConfigGenerator() {
        return new SecurityConfig();
    }
}
