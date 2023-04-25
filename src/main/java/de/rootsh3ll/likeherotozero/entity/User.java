package de.rootsh3ll.likeherotozero.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    boolean isActive;

    @OneToMany(mappedBy = "createdBy")
    List<EmissionData> addedEmissionData;

    @ManyToMany
    @JoinTable(name = "User_UserRole",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userRole_id", referencedColumnName = "id")
    )
    List<UserRole> userRoles;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<EmissionData> getAddedEmissionData() {
        return addedEmissionData;
    }

    public void setAddedEmissionData(List<EmissionData> addedEmissionData) {
        this.addedEmissionData = addedEmissionData;
    }

    public void addAddedEmissionData(EmissionData emissionData) {
        this.addedEmissionData.add(emissionData);
        emissionData.setCreatedBy(this);
    }

    public void removeAddedEmissionData(EmissionData emissionData) {
        this.addedEmissionData.remove(emissionData);
        emissionData.setCreatedBy(null);
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
