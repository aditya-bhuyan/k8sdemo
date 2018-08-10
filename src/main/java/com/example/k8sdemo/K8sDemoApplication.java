package com.example.k8sdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class K8sDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(K8sDemoApplication.class, args);
    }

    @Profile("test")
    @Bean
    public UserDetailsService userDetailsService() {
        // ensure the passwords are encoded properly
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "SYSTEM_ADMIN").build());
        return manager;
    }

    @Configuration
    public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                    .anyRequest().hasRole("SYSTEM_ADMIN")
                    .and()
                    .httpBasic();
        }

    }
}
