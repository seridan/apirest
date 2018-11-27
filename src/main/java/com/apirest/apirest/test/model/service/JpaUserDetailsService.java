package com.apirest.apirest.test.model.service;

import com.apirest.apirest.test.model.dao.IUserDao;
import com.apirest.apirest.test.model.entity.Role;
import com.apirest.apirest.test.model.entity.User;
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


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = iUserDao.findByUsername(username);

        if(user == null) {

            throw new UsernameNotFoundException("Username does not exist in database");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        if(authorities.isEmpty()) {
            throw new UsernameNotFoundException("Username does not have any rol");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities) {
        };
    }



}
