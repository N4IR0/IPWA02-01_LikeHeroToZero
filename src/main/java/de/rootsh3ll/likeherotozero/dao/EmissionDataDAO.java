package de.rootsh3ll.likeherotozero.dao;

import de.rootsh3ll.likeherotozero.entity.Country;
import de.rootsh3ll.likeherotozero.entity.EmissionData;
import de.rootsh3ll.likeherotozero.service.EntityManagerService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.Date;

@Named
public class EmissionDataDAO implements Serializable {
    @Inject
    private EntityManagerService entityManagerService;

    public EmissionData findOneById(int id) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM EmissionData e WHERE id = :id");
        query.setParameter("id", id);

        try {
            return (EmissionData) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public EmissionData findOneByCountryAndDate(Country country, Date reportedForDate) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM EmissionData e WHERE country = :country AND reportedFor = :reportedFor");
        query.setParameter("country", country);
        query.setParameter("reportedFor", reportedForDate);

        try {
            return (EmissionData) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public EmissionData findNewestByCountry(Country country) {
        EntityManager entityManager = entityManagerService.getEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM EmissionData e WHERE country = :country ORDER BY reportedFor DESC LIMIT 1");
        query.setParameter("country", country);

        try {
            return (EmissionData) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
