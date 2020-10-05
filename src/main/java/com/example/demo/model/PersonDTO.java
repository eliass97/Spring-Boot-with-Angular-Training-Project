package com.example.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class PersonDTO {

    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String sex;
    private String countryOfBirthISO;
    private String countryOfResidenceISO;
    private String telephone;
    private String email;
    private Timestamp lastUpdateDate;

    public PersonDTO() {

    }

    public PersonDTO(int id, String fullName, LocalDate dateOfBirth, String sex, String countryOfBirthISO, String countryOfResidenceISO, String telephone, String email, Timestamp lastUpdateDate) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.countryOfBirthISO = countryOfBirthISO;
        this.countryOfResidenceISO = countryOfResidenceISO;
        this.telephone = telephone;
        this.email = email;
        this.lastUpdateDate = lastUpdateDate;
    }

    public PersonDTO(Person person) {
        this(person.getId(), person.getFullName(), person.getDateOfBirth(), person.getSex(), person.getCountryOfBirth().getIso(), person.getCountryOfResidence().getIso(), person.getTelephone(), person.getEmail(), person.getLastUpdateDate());
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getCountryOfBirthISO() {
        return countryOfBirthISO;
    }

    public String getCountryOfResidenceISO() {
        return countryOfResidenceISO;
    }

    public void setCountryOfBirthISO(String countryOfBirthISO) {
        this.countryOfBirthISO = countryOfBirthISO;
    }

    public void setCountryOfResidenceISO(String countryOfResidenceISO) {
        this.countryOfResidenceISO = countryOfResidenceISO;
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person: [" + id + ", " + fullName + ", " + dateOfBirth + ", " + sex + ", " + countryOfBirthISO + ", " + countryOfResidenceISO + ", " + telephone + ", " + email + ", " + lastUpdateDate + "]";
    }
}