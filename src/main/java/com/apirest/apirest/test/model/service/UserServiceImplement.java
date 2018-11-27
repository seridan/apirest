package com.apirest.apirest.test.model.service;

import com.apirest.apirest.test.model.dao.IUserDao;
import com.apirest.apirest.test.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
public class UserServiceImplement implements IUserService{

    @Autowired

    private IUserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);

    }

    @Transactional(readOnly = true)
    @Override
    public User findOne(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.deleteById(id);

    }

   
}
