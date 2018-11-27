package com.apirest.apirest.test.model.dao;

import com.apirest.apirest.test.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface IUserDao extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
