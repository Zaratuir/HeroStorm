package com.herostorm.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public Config(){

    }

    @Bean
    public EventManager getEventManager(){
        return new EventManager();
    }
}
