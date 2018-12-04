package com.apirest.apirest.test.auth.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Configuration
public class SpringDataRestCustomization implements RepositoryRestConfigurer {


        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.getCorsRegistry().addMapping("/**")
                    .allowedOrigins("http://localhost:8080");
        }

}
