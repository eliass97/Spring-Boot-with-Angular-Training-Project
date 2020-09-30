package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String iso;
    private String description;
    private String prefix;

    public Country() {

    }

    public Country(int id, String iso, String description, String prefix) {
        this.id = id;
        this.iso = iso;
        this.description = description;
        this.prefix = prefix;
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
        return "Country: [" + id + ", " + prefix + ", " + description + "]";
    }
}