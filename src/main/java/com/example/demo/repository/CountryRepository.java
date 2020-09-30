package com.example.demo.repository;

import com.example.demo.model.Country;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    @Override
    @Query(value = "SELECT c FROM Country c")
    Iterable<Country> findAll();

    @Query(value = "SELECT c FROM Country c where id=?1")
    Country findById(int id);

    /*
    @Transactional
    @Modifying
    @Query(value = ???)
    Country save(Country newCountry);
    */

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Country where id=?1")
    void deleteById(int id);
}