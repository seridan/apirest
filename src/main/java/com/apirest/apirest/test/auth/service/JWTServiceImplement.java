package com.apirest.apirest.test.auth.service;

import com.apirest.apirest.test.auth.filter.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component //Con esta anotacion la podremos inyectar, como por ejemplo en la clase SpringSecurityConfig.
public class JWTServiceImplement implements JWTService{

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);// Llave secreta.
    public static final Long EXPIRATION_DATE = 14000000L;// Valor del tiempo de expiración.
    public static final String TOKEN_PREFIX = "Bearer ";// Prefijo del token.
    public static final String HEADER_STRING = "Authorization";


    @Override
    public String create(Authentication authentication) throws IOException {
        //Obtenemos el usuario desde authResult, ya que está autenticado y contiene todos los datos del usuario, que
        //pertenece a la clase org.springframework.security.core.userdetails.User;
        String username = ( (User) authentication.getPrincipal()).getUsername();

        //Obtenemos los roles...
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        //... Pero los roles no se pudeden pasar como un metodo setRoles etc, lo haremos obteniendo los Claims
        Claims claims = Jwts.claims();
        //Añadimos la clave authorities y el valor roles, pero debemos pasarlos a JSON.
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        //Creacion y compactacion del token creado a partir del token de la clase Authentication.
        String token = Jwts.builder()
                .setClaims(claims)// Pasamos los roles a través del objeto claims
                .setSubject(username) //También lo podemos obtener directamente: authResult.getName()
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date()) //Fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))// Fecha de expiracion Long (4 horas)
                .compact();
        return token;
    }

    @Override
    public boolean validate(String token) {

        try {

            getClaims(token);

            return true;
        }catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(resolve(token))
                .getBody();

        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws  IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

        return null;
    }

    @Override
    public String resolve(String token) {

        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
