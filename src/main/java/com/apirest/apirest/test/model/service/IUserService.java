package com.apirest.apirest.test.model.service;

import com.apirest.apirest.test.model.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public void save(User user);

    public User findOne(Long id);

    public void delete(Long id);

    //public User findByUserName(String username);

}

