package de.rootsh3ll.likeherotozero.controller;

import de.rootsh3ll.likeherotozero.dao.CountryDAO;
import de.rootsh3ll.likeherotozero.dao.EmissionDataDAO;
import de.rootsh3ll.likeherotozero.entity.Country;
import de.rootsh3ll.likeherotozero.entity.EmissionData;
import de.rootsh3ll.likeherotozero.service.SessionService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class IndexController implements Serializable {
    @Inject
    private EmissionDataDAO emissionDataDAO;
    @Inject
    private CountryDAO countryDAO;
    @Inject
    private SessionService sessionService;

    public EmissionData getNewestEmissionData() {
        String countryCode = sessionService.getLocale().getISO3Country();
        Country country = countryDAO.findOneByCode(countryCode);

        if (null == country) {
            return null;
        }

        return emissionDataDAO.findNewestByCountry(country);
    }
}
