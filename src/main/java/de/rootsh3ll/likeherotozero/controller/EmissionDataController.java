package de.rootsh3ll.likeherotozero.controller;

import de.rootsh3ll.likeherotozero.dao.CountryDAO;
import de.rootsh3ll.likeherotozero.dao.EmissionDataDAO;
import de.rootsh3ll.likeherotozero.dao.UserRoleDAO;
import de.rootsh3ll.likeherotozero.entity.Country;
import de.rootsh3ll.likeherotozero.entity.EmissionData;
import de.rootsh3ll.likeherotozero.entity.User;
import de.rootsh3ll.likeherotozero.entity.UserRole;
import de.rootsh3ll.likeherotozero.service.EntityManagerService;
import de.rootsh3ll.likeherotozero.service.SessionService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class EmissionDataController implements Serializable {
    @Inject
    private SessionService sessionService;
    @Inject
    private CountryDAO countryDAO;
    @Inject
    private UserRoleDAO userRoleDAO;
    @Inject
    private EmissionDataDAO emissionDataDAO;
    @Inject
    private EntityManagerService entityManagerService;

    private int selectedCountryId;

    private EmissionData emissionData = null;

    public EmissionDataController() {

    }

    public int getSelectedCountryId() {
        return selectedCountryId;
    }

    public void setSelectedCountryId(int selectedCountryId) {
        this.selectedCountryId = selectedCountryId;

        this.initializeNewEmissionData();
    }

    public boolean isUserAllowedToCreateOrEdit() {
        User user = sessionService.getUser();
        UserRole requiredUserRole = userRoleDAO.findOneByRoleName("ROLE_REPORTER");

        if (null == user) {
            return false;
        }

        return user.getUserRoles().contains(requiredUserRole);
    }

    public void validateCountryChange(FacesContext context, UIComponent component, Object value) {
        Country country = countryDAO.findOneById((int) value);

        if (null == country) {
            throw new ValidatorException(new FacesMessage("Land nicht gefunden!"));
        }
    }

    public List<EmissionData> getEmissionDataBySelectedCountry() {
        Country country = countryDAO.findOneById(selectedCountryId);

        if (null == country) {
            return new ArrayList<EmissionData>();
        }

        return country.getEmissionData();
    }

    public void initializeNewEmissionData() {
        this.emissionData = new EmissionData();
    }

    public void initializingExistingEmissionData(int emissionDataId) {
        this.emissionData = emissionDataDAO.findOneById(emissionDataId);
    }

    public EmissionData getEmissionData() {
        if (null == this.emissionData) {
            this.emissionData = new EmissionData();
        }

        return this.emissionData;
    }

    public void setEmissionData(EmissionData emissionData) {
        this.emissionData = emissionData;
    }

    public void validateReportedForDate(FacesContext context, UIComponent component, Object value) {
        Date reportedForDate = (Date) value;
        Date currentDate = new Date();

        if (reportedForDate.compareTo(currentDate) > 0) {
            throw new ValidatorException(new FacesMessage("Datum darf nicht in der Zukunft liegen!"));
        }

        Country country = countryDAO.findOneById(selectedCountryId);
        EmissionData emissionData = emissionDataDAO.findOneByCountryAndDate(country, reportedForDate);

        if (null != emissionData && this.emissionData.getId() != emissionData.getId()) {
            throw new ValidatorException(new FacesMessage("Für dieses Datum liegt bereits ein Eintrag vor!"));
        }
    }

    public void validateEmissionValue(FacesContext context, UIComponent component, Object value) {
        double emissionValue = (double) value;

        if (emissionValue < 0) {
            throw new ValidatorException(new FacesMessage("Emissionswert darf nicht negativ sein!"));
        }
    }

    public void saveEmissionData() {
        EntityManager entityManager = entityManagerService.getEntityManager();
        EntityTransaction t = entityManager.getTransaction();

        if (0 == this.emissionData.getId()) {
            Country country = countryDAO.findOneById(selectedCountryId);

            this.emissionData.setCreatedBy(sessionService.getUser());
            this.emissionData.setCreatedAt(new Date());
            this.emissionData.setCountry(country);
        }

        this.emissionData.setUpdatedAt(new Date());

        t.begin();
        entityManager.merge(this.emissionData);
        t.commit();
        entityManager.close();

        this.initializeNewEmissionData();
    }
}
