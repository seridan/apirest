package com.apirest.apirest.test.model.dao;

import com.apirest.apirest.test.model.entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface IUserDao {

    public List<User> findAll();

    public void save(User user);

    public User findOne(Long id);

    public void delete(Long id);
}
