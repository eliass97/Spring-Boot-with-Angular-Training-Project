package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;

import org.springframework.stereotype.Service;

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
        return CountryRepository.findById(id).orElse(null);
    }

    public Country create(Country newCountry) {
        return CountryRepository.save(newCountry);
    }

    public Country update(int id, Country newCountry) {
        newCountry.setId(id);
        return CountryRepository.save(newCountry);
    }

    public void delete(int id) {
        CountryRepository.deleteById(id);
    }
}