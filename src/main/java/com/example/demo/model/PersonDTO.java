package com.example.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public class PersonDTO {

    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String sex;
    private String countryOfBirth;
    private String countryOfResidence;
    private String telephone;
    private String email;
    private Timestamp lastUpdateDate;

    public PersonDTO() {

    }

    public PersonDTO(int id, String fullName, LocalDate dateOfBirth, String sex, String countryOfBirth, String countryOfResidence, String telephone, String email, Timestamp lastUpdateDate) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.countryOfBirth = countryOfBirth;
        this.countryOfResidence = countryOfResidence;
        this.telephone = telephone;
        this.email = email;
        this.lastUpdateDate = lastUpdateDate;
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

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
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
        return "Person: [" + id + ", " + fullName + ", " + dateOfBirth + ", " + sex + ", " + countryOfBirth + ", " + countryOfResidence + ", " + telephone + ", " + email + ", " + lastUpdateDate + "]";
    }

    public static PersonDTO mapper(Person person) {
        return new PersonDTO(person.getId(), person.getFullName(), person.getDateOfBirth(), person.getSex(), person.getCountryOfBirth().getIso(), person.getCountryOfResidence().getIso(), person.getTelephone(), person.getEmail(), person.getLastUpdateDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final PersonDTO other = (PersonDTO) obj;
        if (this.getId() != other.getId()) {
            return false;
        } else if (!Objects.equals(this.getFullName(), other.getFullName())) {
            return false;
        } else if (!Objects.equals(this.getDateOfBirth(), other.getDateOfBirth())) {
            return false;
        } else if (!Objects.equals(this.getSex(), other.getSex())) {
            return false;
        } else if (!Objects.equals(this.getCountryOfBirth(), other.getCountryOfBirth())) {
            return false;
        } else if (!Objects.equals(this.getCountryOfResidence(), other.getCountryOfResidence())) {
            return false;
        } else if (!Objects.equals(this.getEmail(), other.getEmail())) {
            return false;
        } else if (!Objects.equals(this.getTelephone(), other.getTelephone())) {
            return false;
        } else if (!Objects.equals(this.getLastUpdateDate(), other.getLastUpdateDate())) {
            return false;
        } else {
            return true;
        }
    }
}