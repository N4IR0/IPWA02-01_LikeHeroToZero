package de.rootsh3ll.likeherotozero.dao;
import de.rootsh3ll.likeherotozero.entity.UserRole;
import de.rootsh3ll.likeherotozero.service.EntityManagerService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.io.Serializable;

@Named
public class UserRoleDAO implements Serializable {
    @Inject
    private EntityManagerService entityManagerService;

    public UserRole findOneByRoleName(String roleName) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT u FROM UserRole u WHERE role = :role");
        query.setParameter("role", roleName);

        try {
            return (UserRole) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
