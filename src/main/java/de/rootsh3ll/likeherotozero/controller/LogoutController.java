package de.rootsh3ll.likeherotozero.controller;

import de.rootsh3ll.likeherotozero.service.SessionService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class LogoutController implements Serializable {
    @Inject
    private SessionService sessionService;

    public String logout() {
        this.sessionService.setUser(null);

        return "index.xhtml";
    }
}
