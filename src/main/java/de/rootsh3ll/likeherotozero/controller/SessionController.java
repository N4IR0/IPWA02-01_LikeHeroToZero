package de.rootsh3ll.likeherotozero.controller;

import de.rootsh3ll.likeherotozero.service.SessionService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Named
@ViewScoped
public class SessionController implements Serializable {
    @Inject
    private SessionService sessionService;
    private String country;
    private final Map<String, Locale> availableCountries;

    public SessionController() {
        availableCountries = new LinkedHashMap<String, Locale>();

        for (Locale availableLocale : Locale.getAvailableLocales()) {
            availableCountries.put(availableLocale.getCountry(), availableLocale);
        }
    }

    public String getCountry() {
        if (null == this.country) {
            return sessionService.getLocale().getCountry();
        }

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, Locale> getAvailableCountries() {
        return availableCountries;
    }

    public void validateCountryChange(FacesContext context, UIComponent component, Object value) {
        Locale locale = availableCountries.get((String) value);

        if (null == locale) {
            throw new ValidatorException(new FacesMessage("Land nicht verfügbar!"));
        }
    }

    public String changeCountry() {
        Locale locale = this.availableCountries.get(this.country);
        sessionService.setLocale(locale);

        return "index.xhtml";
    }
}
