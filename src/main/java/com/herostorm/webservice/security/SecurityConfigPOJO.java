package com.herostorm.webservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ConfigurationProperties("security")
public class SecurityConfigPOJO {

    private String secret;
    private byte[] secretBytes;
    private String headerString;
    private String tokenPrefix;


    private void generateSecret(){
        secretBytes = new byte[1024];
        new Random().nextBytes(secretBytes);
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public byte[] getSecretBytes(){
        if(secretBytes == null){
            if(this.secret == null){
                generateSecret();
            } else {
                secretBytes = this.secret.getBytes();
            }
        }
        return secretBytes;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecretBytes(byte[] secretBytes) {
        this.secretBytes = secretBytes;
    }

    public String getHeaderString() {
        return headerString;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
