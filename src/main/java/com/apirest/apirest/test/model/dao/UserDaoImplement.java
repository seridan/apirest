package com.apirest.apirest.test.model.dao;

import com.apirest.apirest.test.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("repository")
class UserDaoImplement implements IUserDao {

    @PersistenceContext //Contiene la unidad de persistencia
    private EntityManager em;


    @Override
    public List<User> findAll() {
        return em.createQuery("from User").getResultList();
    }

    @Override
    public User findOne(Long id) {
        return em.find(User.class, id);
    }


    @Override
    public void save(User user) {
        if(user.getId() != null && user.getId() > 0) {
            em.merge(user);
        } else {
            em.persist(user);
        }
    }

    @Override
    public void delete(Long id) {
        User user = findOne(id);
        em.remove(user);
    }

}
