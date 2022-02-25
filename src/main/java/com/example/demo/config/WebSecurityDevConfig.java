package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Profile("dev")
@Configuration
public class WebSecurityDevConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService users() {
        UserDetails userDetails = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$Wf4w1tX3Bo0URZdTgcJX7eqoDCgls8Cw1TjkAaENF0R.2TO4WOP6W") // пароль pswd1
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated() // на любой запрос требуем аутентификацию
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // для AngularJS
                .and().formLogin(); // используем готовую форму логина от Спринга
    }
}
