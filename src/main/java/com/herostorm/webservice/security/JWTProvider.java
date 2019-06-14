package com.herostorm.webservice.security;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

@Component
@ConfigurationProperties("jwt-provider")
public class JWTProvider {

    private int expiryTimeInMinutes;
    private SecurityConfigPOJO securityConfig;
    private UserManager userManager;

    @Inject
    public JWTProvider(SecurityConfigPOJO securityConfig, UserManager userManager){
        this.securityConfig = securityConfig;
        this.userManager = userManager;
    }

    public String generateToken(String userName) {
        User user = userManager.getByUserName(userName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, this.expiryTimeInMinutes);
        long expiryTime = cal.getTimeInMillis();
        String[] roles = new String[user.getUserRoles().size()];
        roles = user.getUserRolesAsString().toArray(roles);
        return JWT.create().withSubject(user.getId()).withArrayClaim("roles", roles).withExpiresAt(new Date(expiryTime)).sign(Algorithm.HMAC512(securityConfig.getSecretBytes()));
    }

    public User getUserFromToken(String token) throws SignatureVerificationException {
        String userID = JWT.require(Algorithm.HMAC512(securityConfig.getSecretBytes()))
                .build().verify(token.replace(securityConfig.getTokenPrefix(),"")).getSubject();
        return userManager.getUser(userID);
    }

    public String getIDFromToken(String jwt){
        return JWT.decode(jwt).getSubject();
    }

    public void setExpiryTimeInMinutes(int expiryTimeInMinutes) {
        this.expiryTimeInMinutes = expiryTimeInMinutes;
    }
}
