package com.apirest.apirest.test.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;

public class JWTAuthorizationFilter  extends BasicAuthenticationFilter {


    public static final Key SECRET_KEY = JWTAuthenticationFilter.SECRET_KEY;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (!requiresAuthentication(header)) { //(header == null || !header.toLowerCase().startsWith("Bearer ")
            chain.doFilter(request, response);
            return;
        }

        boolean tokenValid;
        Claims token = null;

        try {

            token = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(header.replace("Bearer ", ""))
                    .getBody();
            tokenValid = true;
        }catch (JwtException | IllegalArgumentException e) {

            tokenValid =false;
        }

        UsernamePasswordAuthenticationToken authentication = null;

        if(tokenValid) {
            String username = token.getSubject();
            Object roles = token.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                    .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

            authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    protected boolean requiresAuthentication(String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }

        return true;
    }

}
