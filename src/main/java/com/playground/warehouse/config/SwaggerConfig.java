package com.playground.warehouse.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.playground.warehouse"})
public class SwaggerConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
