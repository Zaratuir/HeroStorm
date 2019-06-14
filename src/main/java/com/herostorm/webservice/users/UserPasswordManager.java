package com.herostorm.webservice.users;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@ConfigurationProperties("user")
public class UserPasswordManager {
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public UserPasswordManager(){

    }

    public String hashPassword(String password){
        try{
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.secretKey.getBytes(),"HmacSHA512");
            hmac.init(secretKeySpec);
            return Base64.encodeBase64String(hmac.doFinal(password.getBytes()));
        } catch(NoSuchAlgorithmException e){
            System.out.println("Invalid Algorithm");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to process password.");
        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process password.");
        }
    }

    public boolean testPassword(User user, String password){
        return user.getPassword().equals(this.hashPassword(password));
    }
}
