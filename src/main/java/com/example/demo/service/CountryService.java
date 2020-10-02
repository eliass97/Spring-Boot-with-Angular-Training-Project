package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
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

    public Country findById(int id) throws DemoException {
        Optional<Country> result = CountryRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Country not found");
        }
        return result.get();
    }

    public Country create(Country newCountry) {
        return CountryRepository.save(newCountry);
    }

    public Country update(int id, Country newCountry) throws DemoException {
        if (newCountry.getId() != id && newCountry.getId() != 0) {
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        findById(id);
        newCountry.setId(id);
        return CountryRepository.save(newCountry);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        CountryRepository.deleteById(id);
    }
}