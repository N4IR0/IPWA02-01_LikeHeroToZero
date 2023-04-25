package de.rootsh3ll.likeherotozero.dao;

import de.rootsh3ll.likeherotozero.entity.Country;
import de.rootsh3ll.likeherotozero.service.EntityManagerService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.List;

@Named
public class CountryDAO implements Serializable {
    @Inject
    private EntityManagerService entityManagerService;


    public List<Country> findAll() {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Country c");
        return query.getResultList();
    }

    public Country findOneById(int countryId) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Country c WHERE id = :id");
        query.setParameter("id", countryId);

        try {
            return (Country) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Country findOneByCode(String countyCode) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Country c WHERE code = :code");
        query.setParameter("code", countyCode);

        try {
            return (Country) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
