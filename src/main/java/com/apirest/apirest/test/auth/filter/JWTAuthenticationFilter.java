package com.apirest.apirest.test.auth.filter;



import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);// Llave secreta.

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request); //Seria lo mismo que request.getParameter("username");
        String password = obtainPassword(request); //Seria lo mismo que request.getParameter("password");



        if(username != null && password != null) {
            logger.info("Username from request parameter (form-data): " + username);
            logger.info("Password from request parameter (form-data): " + password);

        } else { //Cuando los datos recibidos son JSON los convertimos en un objeto tipo Usuario de la clase entity
            com.apirest.apirest.test.model.entity.User user = null;
            try {
                //Creamos un nuevo usuario obteniéndolo desde el JSON del request
                user = new ObjectMapper().readValue(request.getInputStream(),
                        com.apirest.apirest.test.model.entity.User.class);
                //Obtenemos el nombre del usuario y password del objeto user obtenido anteriormente y lo pasamos.
                username = user.getUsername();
                password = user.getPassword();

                logger.info("Username from request ImputStream (raw): " + username);
                logger.info("Password from request ImputStream (raw): " + password);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (username != null) {
            username = username.trim();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //Obtenemos el usuario desde authResult, ya que está autenticado y contiene todos los datos del usuario, que
        //pertenece a la clase org.springframework.security.core.userdetails.User;
        String username = ( (User) authResult.getPrincipal()).getUsername();

        //Obtenemos los roles...
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
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
                .setExpiration(new Date(System.currentTimeMillis() + 14000000L))// Fecha de expiracion Long (4 horas)
                .compact();


        //Pasamos el token en la cabecera de la respuesta para el usuario. Prefijo Bearer para pasar el token al cliente
        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token); //Pasamos el token
        body.put("user", (User) authResult.getPrincipal());// El user
        body.put("message", String.format("Hello %s, you have successfully logged in", username));// Mensaje

        //Obtenemos el writer de la respuesta
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));//Lo pasamos a JSON
        response.setStatus(200);//Status 200 ok
        response.setContentType("application/json");//Indicamos que retornamos un JSON
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        //Agregamos un mensaje cuando hay un error de auntenticación.
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Authentication error: incorrect username or password!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));//Lo pasamos a JSON
        response.setStatus(401);//No autorizado.
        response.setContentType("application/json");//Indicamos que retornamos un JSON
    }
}
