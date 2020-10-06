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

    public Country update(int pathId, Country updatedCountry) throws DemoException {
        Country countryToBeUpdated = findById(pathId);
        updateChecks(pathId, updatedCountry, countryToBeUpdated);
        Country result = updateAndSaveInDatabase(updatedCountry, countryToBeUpdated);
        LOGGER.info("CountryService -> PUT -> update -> Updated {}", result);
        return result;
    }

    public void delete(int id) throws DemoException {
        findById(id);
        CountryRepository.deleteById(id);
        LOGGER.info("CountryService -> POST -> delete -> Deleted country with id = {}", id);
    }

    private void updateChecks(int pathId, Country updatedCountry, Country countryFromDatabase) throws DemoException {
        if (updatedCountry.getId() != pathId && updatedCountry.getId() != 0) {
            LOGGER.error("CountryService -> PUT -> basicUpdateChecks -> BadRequestException for path_id = {} and body_id = {}", pathId, updatedCountry.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if (updatedCountry.getLastUpdateDate() == null) {
            LOGGER.error("CountryService -> PUT -> basicUpdateChecks -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        if (!updatedCountry.getLastUpdateDate().equals(countryFromDatabase.getLastUpdateDate())) {
            LOGGER.error("CountryService -> PUT -> update -> ConflictException for {}", updatedCountry);
            throw new ConflictException("Different country versions during update");
        }
    }

    private Country updateAndSaveInDatabase(Country updatedCountry, Country countryToBeUpdated) {
        countryToBeUpdated.setIso(updatedCountry.getIso());
        countryToBeUpdated.setDescription(updatedCountry.getDescription());
        countryToBeUpdated.setPrefix(updatedCountry.getPrefix());
        return CountryRepository.save(countryToBeUpdated);
    }
}