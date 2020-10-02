package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "State")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_id_seq")
    @SequenceGenerator(schema = "public", name = "state_id_seq", sequenceName = "state_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "country_id")
    private int country_id;

    public State() {

    }

    public State(int id, String description, int country_id) {
        this.id = id;
        this.description = description;
        this.country_id = country_id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "State: [" + id + ", " + description + ", " + country_id + "]";
    }
}