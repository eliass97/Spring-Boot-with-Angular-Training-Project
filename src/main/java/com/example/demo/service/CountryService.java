package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository CountryRepository;

    public CountryService(CountryRepository CountryRepository) {
        this.CountryRepository = CountryRepository;
    }

    public Iterable<Country> findAll() {
        return CountryRepository.findAll();
    }

    public Country findById(int id) {
        Optional<Country> result = CountryRepository.findById(id);
        return (Country) result.orElse(null);
    }

    public Country createCountry(Country newCountry) {
        if(NonAcceptableBody(newCountry)) {
            return null;
        }
        return CountryRepository.save(newCountry);
    }

    public Country updateCountry(int id, Country newCountry) {
        newCountry.setId(id);
        return CountryRepository.save(newCountry);
    }

    public void deleteCountry(int id) {
        CountryRepository.deleteById(id);
    }

    public boolean NonAcceptableBody(Country newCountry) {
        return newCountry.getIso() == null || newCountry.getDescription() == null || newCountry.getPrefix() == null;
    }
}