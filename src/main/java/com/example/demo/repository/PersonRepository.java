package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    //@Query(value = "SELECT p FROM Person p WHERE country_of_birth = ?1 OR country_of_residence = ?1")
    //Iterable<Person> findAllByCountryId(int id);
}