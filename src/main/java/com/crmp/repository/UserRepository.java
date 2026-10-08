package com.crmp.repository;

import com.crmp.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for User entities using Hibernate.
 */
@Repository
@Transactional
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    /** Persist a new user. */
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    /** Find a user by username (used by Spring Security). */
    public User findByUsername(String username) {
        List<User> users = sessionFactory.getCurrentSession()
                .createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .list();
        return users.isEmpty() ? null : users.get(0);
    }

    /** Retrieve all users. */
    public List<User> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User u ORDER BY u.fullName", User.class)
                .list();
    }
}
