package com.dev.api_loja.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiLojaConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
