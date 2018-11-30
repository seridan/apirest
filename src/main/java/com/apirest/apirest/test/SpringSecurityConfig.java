package com.apirest.apirest.test;


import com.apirest.apirest.test.auth.handler.LoginSuccessHandler;
import com.apirest.apirest.test.model.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/list").hasRole("STANDARD")
                .antMatchers("/api/save","/api/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.successHandler(successHandler)
                .permitAll()
                .and()
                .csrf().disable()
                .logout().permitAll();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }




}
