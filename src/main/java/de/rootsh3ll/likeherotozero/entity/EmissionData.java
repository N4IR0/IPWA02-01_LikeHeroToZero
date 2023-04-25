package de.rootsh3ll.likeherotozero.entity;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.util.Date;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "reportedFor", "country_id" }) })
public class EmissionData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date reportedFor;

    @ManyToOne
    @JoinColumn(nullable = false)
    Country country;

    @ManyToOne
    @JoinColumn
    User createdBy;

    @Column(nullable = false)
    double value;

    public EmissionData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getValue() {
        return value;
    }

    public String getRoundedValue() {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        return decimalFormat.format(value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getReportedFor() {
        return reportedFor;
    }

    public void setReportedFor(Date reportedFor) {
        this.reportedFor = reportedFor;
    }
}
