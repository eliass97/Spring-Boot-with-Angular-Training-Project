package com.example.demo.service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
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

    public Country findById(int id) throws ResourceNotFoundException {
        Optional<Country> result = CountryRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Country ID not found in database");
        }
        return result.get();
    }

    public Country create(Country newCountry) {
        return CountryRepository.save(newCountry);
    }

    public Country update(int id, Country newCountry) throws UpdateIdMismatchException, ResourceNotFoundException {
        if (newCountry.getId() != id && newCountry.getId() != 0) {
            throw new UpdateIdMismatchException("Path ID variable does not match with body ID");
        }
        findById(id);
        newCountry.setId(id);
        return CountryRepository.save(newCountry);
    }

    public void delete(int id) throws ResourceNotFoundException {
        findById(id);
        CountryRepository.deleteById(id);
    }
}