package com.herostorm.webservice.security;

import com.herostorm.webservice.users.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.inject.Inject;
import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class HTTPSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticateUserService userDetailsService;
    private JWTAuthorizationFilter authorizationFilter;

    @Inject
    public HTTPSecurityConfig(AuthenticateUserService userDetailsService, JWTAuthorizationFilter authorizationFilter){
        this.userDetailsService = userDetailsService;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/Users/{userID}/**").access("@webSecurity.checkUserID(authentication,#userID) or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/Users").hasRole("ADMIN")
                .antMatchers("/Users/Validate").permitAll()
                .anyRequest().authenticated()
        .and()
            .addFilter(authorizationFilter);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean("webSecurity")
    @Inject
    public WebSecurity getWebSecurity(UserManager userManager){
        return new WebSecurity(userManager);
    }

    @Bean("corsConfigurationSource")
    CorsConfigurationSource getCorsConf(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        cors.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
