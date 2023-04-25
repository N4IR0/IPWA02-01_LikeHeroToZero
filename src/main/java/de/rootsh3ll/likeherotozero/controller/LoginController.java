package de.rootsh3ll.likeherotozero.controller;

import de.rootsh3ll.likeherotozero.dao.UserDAO;
import de.rootsh3ll.likeherotozero.entity.User;
import de.rootsh3ll.likeherotozero.service.SessionService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionService sessionService;
    private String username;
    private String password;

    public void postValidateName(ComponentSystemEvent event) {
        UIInput temp = (UIInput) event.getComponent();
        this.username = (String) temp.getValue();
    }

    public String getEncodePassword() {
        return bCryptPasswordEncoder.encode("test");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        // We do not want to read entered clear text passwords
        return "";
    }

    public void validateLogin(FacesContext context, UIComponent component, Object value) {
        User user = this.userDAO.findOneByUsername(this.username);

        if (null == user) {
            throw new ValidatorException(new FacesMessage("Login fehlgeschlagen!"));
        }

        if (!this.bCryptPasswordEncoder.matches((String) value, user.getPassword())) {
            throw new ValidatorException(new FacesMessage("Login fehlgeschlagen!"));
        }
    }

    public String login() {
        User user = this.userDAO.findOneByUsername(this.username);

        if (null == user) {
            return "login.xhtml";
        }

        if (!this.bCryptPasswordEncoder.matches(this.password, user.getPassword())) {
            return "login.xhtml";
        }

        this.sessionService.setUser(user);
        return "index.xhtml";
    }
}
