package com.example.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_seq")
    @SequenceGenerator(schema = "public", name = "country_id_seq", sequenceName = "country_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "iso")
    private String iso;

    @Column(name = "description")
    private String description;

    @Column(name = "prefix")
    private String prefix;

    @Version
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    @OneToMany(mappedBy = "countryOfBirth", targetEntity = Person.class)
    private List<Person> origins;

    @OneToMany(mappedBy = "countryOfResidence", targetEntity = Person.class)
    private List<Person> residents;

    public Country() {

    }

    public Country(int id, String iso, String description, String prefix, Timestamp lastUpdateDate) {
        this.id = id;
        this.iso = iso;
        this.description = description;
        this.prefix = prefix;
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Person> getOrigins() {
        return origins;
    }

    public List<Person> getResidents() {
        return residents;
    }

    public void setOrigins(List<Person> origins) {
        this.origins = origins;
    }

    public void setResidents(List<Person> residents) {
        this.residents = residents;
    }

    public String getIso() {
        return iso;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "Country: [" + id + ", " + iso + ", " + prefix + ", " + description + ", " + lastUpdateDate + "]";
    }
}