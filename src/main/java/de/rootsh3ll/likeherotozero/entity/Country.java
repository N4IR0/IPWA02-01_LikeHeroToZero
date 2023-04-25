package de.rootsh3ll.likeherotozero.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(length = 3, nullable = false, unique = true)
    String code;

    @OneToMany(mappedBy = "country")
    List<EmissionData> emissionData;

    public Country() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<EmissionData> getEmissionData() {
        return emissionData;
    }

    public void setEmissionData(List<EmissionData> emissionData) {
        this.emissionData = emissionData;
    }

    public void addEmissionData(EmissionData emissionData) {
        this.emissionData.add(emissionData);
        emissionData.setCountry(this);
    }

    public void removeEmissionData(EmissionData emissionData) {
        this.emissionData.remove(emissionData);
        emissionData.setCountry(null);
    }
}
