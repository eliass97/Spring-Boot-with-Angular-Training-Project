package com.example.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "Person")
public class Person extends DemoObject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
    @SequenceGenerator(schema = "public", name = "person_id_seq", sequenceName = "person_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "sex")
    private String sex;

    @ManyToOne
    @JoinColumn(name = "country_of_birth_id")
    private Country countryOfBirth;

    @ManyToOne
    @JoinColumn(name = "country_of_residence_id")
    private Country countryOfResidence;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    public Person() {

    }

    public Person(int id, String fullName, LocalDate dateOfBirth, String sex, Country countryOfBirth, Country countryOfResidence, String telephone, String email, Timestamp lastUpdateDate) {
        this.setLastUpdateDate(lastUpdateDate);
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.countryOfBirth = countryOfBirth;
        this.countryOfResidence = countryOfResidence;
        this.telephone = telephone;
        this.email = email;
    }

    public Person(PersonDTO persondto, Country countryOfBirth, Country countryOfResidence) {
        this(persondto.getId(), persondto.getFullName(), persondto.getDateOfBirth(), persondto.getSex(), countryOfBirth, countryOfResidence, persondto.getTelephone(), persondto.getEmail(), persondto.getLastUpdateDate());
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public Country getCountryOfBirth() {
        return countryOfBirth;
    }

    public Country getCountryOfResidence() {
        return countryOfResidence;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCountryOfBirth(Country countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public void setCountryOfResidence(Country countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person: [" + id + ", " + fullName + ", " + dateOfBirth + ", " + sex + ", " + countryOfBirth.getId() + ", " + countryOfResidence.getId() + ", " + telephone + ", " + email + ", " + getLastUpdateDate() + "]";
    }
}