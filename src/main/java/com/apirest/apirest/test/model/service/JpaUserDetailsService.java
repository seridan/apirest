package com.apirest.apirest.test.model.service;

import com.apirest.apirest.test.model.dao.IUserDao;
import com.apirest.apirest.test.model.entity.Role;
import com.apirest.apirest.test.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService, Serializable {

    @Autowired
    private IUserDao iUserDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = iUserDao.findByUsername(username);

        if(user == null) {
            logger.error("Login error: user '" + username + "' does not exists in database!");
            throw new UsernameNotFoundException("Username does not exist in database");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRoles()) {
            logger.info("Role: ".concat(role.getRoleName()));
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        if(authorities.isEmpty()) {
            logger.error("Login error: User '" + username + "' does not have assigned roles!");
            throw new UsernameNotFoundException("Username does not have any rol");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);

    }

}
