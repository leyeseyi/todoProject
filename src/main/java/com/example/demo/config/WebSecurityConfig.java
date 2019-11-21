/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Leyeseyi
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception{
        
        http.csrf().disable();
        http.headers().frameOptions().disable();
            http
                .authorizeRequests()
                .antMatchers("/todo").authenticated() 
                .anyRequest().permitAll()              
                .and()
            .formLogin()
                .loginPage("/login")                   
                .permitAll()
                .defaultSuccessUrl("/todo", true) 
                .and()
            .logout()
                .permitAll();
    }
    
    @Bean
    public BCryptPasswordEncoder encodePWD(){
        
        return new BCryptPasswordEncoder();
    }

    
}
