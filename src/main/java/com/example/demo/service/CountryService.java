package com.example.demo.service;

import com.example.demo.repository.CountryRepository;
import com.example.demo.model.Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public Iterable<Country> findAll() {
        return repository.findAll();
    }

    public Country findById(int id) {
        return repository.findById(id);
    }

    public Country createCountry(Country newCountry) {
        return repository.save(newCountry);
    }

    public Country updateCountry(Country newCountry, int id) {
        return newCountry;
        /*
        return repository.findById(id)
                .map(country -> {
                    country.setIso(newCountry.getIso());
                    country.setDescription(newCountry.getDescription());
                    country.setPrefix(newCountry.getPrefix());
                    return repository.save(country);
                })
                .orElseGet(() -> {
                    newCountry.setId(id);
                    return repository.save(newCountry);
                });
         */
    }

    public void deleteCountry(int id) {
        repository.deleteById(id);
    }
}