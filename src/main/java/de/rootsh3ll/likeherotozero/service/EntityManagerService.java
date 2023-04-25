package de.rootsh3ll.likeherotozero.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;

@ApplicationScoped
public class EntityManagerService implements Serializable {
    private final EntityManagerFactory emf;

    private EntityManager em;

    public EntityManagerService() {
        this.emf = Persistence.createEntityManagerFactory("default");
    }

    public EntityManager getEntityManager() {
        if (null == em || !em.isOpen()) {
            this.em = emf.createEntityManager();
        }

        return this.em;
    }
}
