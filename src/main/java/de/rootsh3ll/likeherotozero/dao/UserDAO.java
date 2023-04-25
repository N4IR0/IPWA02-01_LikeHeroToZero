package de.rootsh3ll.likeherotozero.dao;

import de.rootsh3ll.likeherotozero.entity.User;
import de.rootsh3ll.likeherotozero.service.EntityManagerService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.io.Serializable;

public class UserDAO implements Serializable {
    @Inject
    private EntityManagerService entityManagerService;

    public User findOneByUsername(String username) {
        EntityManager em = entityManagerService.getEntityManager();

        Query query = em.createQuery("SELECT u FROM User u WHERE username = :username");
        query.setParameter("username", username);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
