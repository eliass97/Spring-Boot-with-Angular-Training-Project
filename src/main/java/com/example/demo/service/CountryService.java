package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    private final CountryRepository CountryRepository;

    public CountryService(CountryRepository CountryRepository) {
        this.CountryRepository = CountryRepository;
    }

    public List<Country> findAll() {
        LOGGER.info("CountryService -> GET -> Searched for all");
        return CountryRepository.findAll();
    }

    public Country findById(int id) throws DemoException {
        Optional<Country> result = CountryRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.error("CountryService -> GET -> findById -> NotFoundException for id = {}", id);
            throw new NotFoundException("Country not found");
        }
        LOGGER.info("CountryService -> GET -> findById -> Searched for id = {}", id);
        return result.get();
    }

    public Country create(Country newCountry) {
        LOGGER.info("CountryService -> POST -> create -> Created {}", newCountry.toString());
        return CountryRepository.save(newCountry);
    }

    public Country update(int id, Country updatedCountry) throws DemoException {
        if (updatedCountry.getId() != id && updatedCountry.getId() != 0) {
            LOGGER.error("CountryService -> PUT -> update -> BadRequestException for path_id = {} and body_id = {}", id, updatedCountry.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if(updatedCountry.getLastUpdateDate() == null) {
            LOGGER.error("CountryService -> PUT -> update -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        Country countryFromDatabase = findById(id);
        if (!updatedCountry.getLastUpdateDate().equals(countryFromDatabase.getLastUpdateDate())) {
            LOGGER.error("CountryService -> PUT -> update -> ConflictException for {}", updatedCountry.toString());
            throw new ConflictException("Different country versions during update");
        }
        countryFromDatabase.setIso(updatedCountry.getIso());
        countryFromDatabase.setDescription(updatedCountry.getDescription());
        countryFromDatabase.setPrefix(updatedCountry.getPrefix());
        Country result = CountryRepository.save(countryFromDatabase);
        LOGGER.info("CountryService -> PUT -> update -> Updated {}", result.toString());
        return result;
    }

    public void delete(int id) throws DemoException {
        findById(id);
        LOGGER.info("CountryService -> POST -> delete -> Deleted country with id = {}", id);
        CountryRepository.deleteById(id);
    }
}