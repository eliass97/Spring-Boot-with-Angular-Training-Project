package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    private final CountryRepository CountryRepository;

    public CountryService(CountryRepository CountryRepository) {
        this.CountryRepository = CountryRepository;
    }

    public Iterable<Country> findAll() {
        logger.info("GET -> findAll");
        return CountryRepository.findAll();
    }

    public Country findById(int id) throws DemoException {
        Optional<Country> result = CountryRepository.findById(id);
        if (result.isEmpty()) {
            logger.info("GET -> findById -> NotFoundException for id = " + id);
            throw new NotFoundException("Country not found");
        }
        logger.info("GET -> findById -> Successful for id = " + id);
        return result.get();
    }

    public Country create(Country newCountry) {
        logger.info("POST -> create -> " + newCountry.toString());
        return CountryRepository.save(newCountry);
    }

    public Country update(int id, Country newCountry) throws DemoException {
        if (newCountry.getId() != id && newCountry.getId() != 0) {
            logger.info("PUT -> update -> BadRequestException for path_id = " + id + " and body_id = " + newCountry.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        Country result = findById(id);
        result.setIso(newCountry.getIso());
        result.setDescription(newCountry.getDescription());
        result.setPrefix(newCountry.getPrefix());
        logger.info("PUT -> update -> Updated "+result.toString());
        return CountryRepository.save(result);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        logger.info("POST -> delete -> Deleted country with id = " + id);
        CountryRepository.deleteById(id);
    }
}