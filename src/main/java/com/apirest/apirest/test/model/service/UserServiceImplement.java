package com.apirest.apirest.test.model.service;

import com.apirest.apirest.test.model.dao.IUserDao;
import com.apirest.apirest.test.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service("service")
public class UserServiceImplement implements IUserService{

    @Autowired
    @Qualifier("repository")
    private IUserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);

    }

    @Transactional(readOnly = true)
    @Override
    public User findOne(Long id) {
        return userDao.findOne(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);

    }
}
