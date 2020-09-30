package com.example.demo.repository;

import com.example.demo.model.Country;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    @Query(value = "SELECT * FROM Country where id=?1")
    Country findById(int id);
}