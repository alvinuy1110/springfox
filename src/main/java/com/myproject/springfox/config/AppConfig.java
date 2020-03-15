package com.myproject.springfox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.myproject.springfox.domain.Location;
import com.myproject.springfox.jackson.CustomLocationSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule("SimpleModule");
        simpleModule.addSerializer(Location.class, new CustomLocationSerializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
