package de.rootsh3ll.likeherotozero.service;

import de.rootsh3ll.likeherotozero.entity.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class SessionService implements Serializable {
    private User user;
    private Locale locale;

    public Locale getLocale() {
        if (null == locale) {
            return FacesContext.getCurrentInstance().getViewRoot().getLocale();
        }

        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return null != this.getUser();
    }


}
