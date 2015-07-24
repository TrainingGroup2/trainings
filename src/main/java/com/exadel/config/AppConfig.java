package com.exadel.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;


@Configuration
@Component
@ComponentScan({ "com.exadel.*" })
@Import({ SecurityConfig.class })
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppConfig {

   @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/trainings");
        driverManagerDataSource.setUsername("exadel");
        driverManagerDataSource.setPassword("exadel");
        return driverManagerDataSource;
    }

}