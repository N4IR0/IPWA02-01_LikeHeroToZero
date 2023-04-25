package de.rootsh3ll.likeherotozero.entity;

import jakarta.persistence.*;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false, unique = true)
    String role;

    public UserRole() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserRole) {
            UserRole b = (UserRole) o;
            return b.getId() == this.id && b.getRole().equals(this.role);
        }
        return false;
    }
}
