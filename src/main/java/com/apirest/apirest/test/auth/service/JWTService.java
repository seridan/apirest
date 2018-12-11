package com.apirest.apirest.test.auth.service;

/**
Contiene todos los metodos relacionados con la api JWT.
Esta clase nos provee, en una unica sola clase centralizada, trabajar con el token.
 **/

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public interface JWTService {

    /**
     * Método que se encarga de crear el token.
     * @param authentication Nos permite obtener los roles y el username.
     */
    public String create(Authentication authentication) throws IOException;


    /**
     * Valida el token
     * @param token el token a validar
     */
    public boolean validate(String token);


    /**
     * Método para obtener los Claims
     * @param token
     */
    public Claims getClaims(String token);


    /**
     *Obtiene el username desde el string del token.
     * @param token
     */
    public String getUsername(String token);

    /**
     * Obtiene los roles desde el token.
     * El token entrega un formato en json y hay que pasarlo a un objeto
     * Collection de GrantedAuthority mediante el ObjectMapper().
     * @param token de donde obtenemos los roles.
     */
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

    /**
     * Método para resolver el token, quitar el Bearer
     * y obtener solamente el codigo del token.
     * @param token
     */
    public String resolve(String token);




}

