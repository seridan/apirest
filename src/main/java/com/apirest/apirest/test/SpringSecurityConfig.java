package com.apirest.apirest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/list").permitAll()
                .antMatchers("/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/save/user").hasAnyRole("STANDAR")
                .anyRequest().authenticated();
    }

  /*  @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        builder.inMemoryAuthentication()
                .withUser(String.valueOf(userBuilder.username("admin").password("12345").roles("ADMIN", "STANDAR")));


    }*/

}
