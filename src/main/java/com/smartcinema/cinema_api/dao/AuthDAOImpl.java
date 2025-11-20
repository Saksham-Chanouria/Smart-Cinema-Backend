package com.smartcinema.cinema_api.dao;

import com.smartcinema.cinema_api.entities.Role;
import com.smartcinema.cinema_api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthDAOImpl implements AuthDAO{
    private EntityManager entityManager;

    @Autowired
    public AuthDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    public boolean existsByEmail(String email){
        TypedQuery<User> typedQuery = entityManager.createQuery("from User where email = :email",User.class);
        typedQuery.setParameter("email",email);

        List<User> theList = typedQuery.getResultList();
        return !theList.isEmpty();
    }


    public User save(User theUser){
        entityManager.persist(theUser);
        return theUser;
    }

    public Role saveRole(Role theRole){
        entityManager.persist(theRole);
        return  theRole;
    }

    @Override
    public User findByEmail(String email){
        TypedQuery<User> theQuery = entityManager.createQuery("from User where email = :email",User.class);
        theQuery.setParameter("email",email);
        List<User> users = theQuery.getResultList();

        return users.isEmpty() ? null : users.get(0);
    }
}
