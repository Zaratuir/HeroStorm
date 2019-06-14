package com.herostorm.webservice.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserCredentials;
import com.herostorm.webservice.users.UserDetail;
import com.herostorm.webservice.users.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private SecurityConfigPOJO securityConfig;
    private JWTProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Inject
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, SecurityConfigPOJO securityConfig, JWTProvider jwtProvider) {
        super(authenticationManager, authenticationEntryPoint);
        this.authenticationManager = authenticationManager;
        this.securityConfig = securityConfig;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        SecurityContext sc = SecurityContextHolder.getContext();
        if(request.getRequestURI().equals("/Users/Validate")){
            String[] credentials = getCredentialsFromHeader(request.getHeader(securityConfig.getHeaderString()));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credentials[0],credentials[1],new ArrayList<>());
            authenticationManager.authenticate(authentication);
            if(authentication.isAuthenticated()){
                String JWT = jwtProvider.generateToken(credentials[0]);
                response.addHeader(securityConfig.getHeaderString(), securityConfig.getTokenPrefix() + JWT );
                sc.setAuthentication(authentication);
            }
        } else {
            String header = request.getHeader(securityConfig.getHeaderString());
            if(header == null || !header.startsWith(securityConfig.getTokenPrefix())){
                sendInvalidToken(request,response);
                return;
            }
            try{
                UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
                authenticationManager.authenticate(authentication);
                sc.setAuthentication(authentication);
            } catch(SignatureVerificationException e){
                sendInvalidToken(request,response);
                return;
            }
        }
        chain.doFilter(request,response);
    }

    private void sendInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> parts = new ArrayList<String>();
        parts.add("\"timestamp\": \"" + new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()) + "\"");
        parts.add("\"status\": 400");
        parts.add("\"error\": \"Bad Request\"");
        parts.add("\"message\": \"Invalid Token\"");
        parts.add("\"path\": \"" + request.getRequestURI() + "\"");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\r\n");
        for(int i = 0; i < parts.size(); i++) {
            buffer.append("\t" + parts.get(i));
            if(i != parts.size() - 1) {
                buffer.append(",");
            }
            buffer.append("\r\n");
        }
        buffer.append("}");
        String outputString = buffer.toString();
        response.getWriter().write(outputString);
        response.setStatus(400);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(token != null){
            User user = jwtProvider.getUserFromToken(token);
            UserDetail userDetail = new UserDetail(user);
            if(user != null){
                return new UsernamePasswordAuthenticationToken(user.getUserName(),null, userDetail.getAuthorities());
            }
        }
        return null;
    }

    private String[] getCredentialsFromHeader(String authHeader){
        String credentials = new String(Base64.getDecoder().decode(authHeader.substring("Basic".length()).trim()), StandardCharsets.UTF_8);
        String[] returnCreds = new String[2];
        returnCreds[0] = credentials.substring(0,credentials.indexOf(":"));
        returnCreds[1] = credentials.substring(credentials.indexOf(":") + 1, credentials.length());
        return returnCreds;
    }
}