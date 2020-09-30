package com.example.demo.repository;

import com.example.demo.model.Country;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    /*
    @Query(value = "SELECT c FROM Country c")
    Iterable<Country> findAll();

    @Query(value = ???)
    Country save(Country newCountry);
    */

    @Query(value = "SELECT c FROM Country c where id=?1")
    Country findById(int id);

    @Query(value = "DELETE c FROM Country c where id=?1")
    void deleteById(int id);
}