package com.beta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() { return new BCryptPasswordEncoder(5); }

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").access("hasRole('Admin')")
                    .antMatchers("/user/**").access("hasAnyRole('Admin','User')")
                                    .antMatchers("/user/**").permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                    .formLogin().permitAll()
                .successHandler(successHandler);
    }
}
