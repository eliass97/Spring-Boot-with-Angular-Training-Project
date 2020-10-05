package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.model.Person;
import com.example.demo.model.PersonDTO;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    private final PersonRepository PersonRepository;
    private final CountryRepository CountryRepository;

    public PersonService(PersonRepository PersonRepository, CountryRepository CountryRepository) {
        this.PersonRepository = PersonRepository;
        this.CountryRepository = CountryRepository;
    }

    public List<PersonDTO> findAll() {
        List<Person> result = PersonRepository.findAll();
        LinkedList<PersonDTO> resultDTO = new LinkedList<>();
        for (Person person : result) {
            resultDTO.add(new PersonDTO(person));
        }
        LOGGER.info("PersonService -> GET -> findAll -> Searched for all");
        return resultDTO;
    }

    public PersonDTO findById(int id) throws DemoException {
        Optional<Person> result = PersonRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.error("PersonService -> GET -> findById -> NotFoundException for id = {}", id);
            throw new NotFoundException("Person not found");
        }
        LOGGER.info("PersonService -> GET -> findById -> Searched for id = {}", id);
        return new PersonDTO(result.get());
    }

    public PersonDTO create(PersonDTO newPersonDTO) throws DemoException {
        Country countryOfBirth = getCountryByIso(newPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = getCountryByIso(newPersonDTO.getCountryOfResidenceISO());
        Person newPerson = new Person(newPersonDTO, countryOfBirth, countryOfResidence);
        Person result = PersonRepository.save(newPerson);
        LOGGER.info("PersonService -> POST -> create -> Created {}", newPersonDTO.toString());
        return new PersonDTO(result);
    }

    public PersonDTO update(int id, PersonDTO updatedPersonDTO) throws DemoException {
        if (updatedPersonDTO.getId() != id && updatedPersonDTO.getId() != 0) {
            LOGGER.error("PersonService -> updateChecks -> BadRequestException for path_id = {} and body_id = {}", id, updatedPersonDTO.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if (updatedPersonDTO.getLastUpdateDate() == null) {
            LOGGER.error("CountryService -> PUT -> update -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        PersonDTO personDTOFromDatabase = findById(id);
        if (!updatedPersonDTO.getLastUpdateDate().equals(personDTOFromDatabase.getLastUpdateDate())) {
            LOGGER.error("PersonService -> updateChecks -> ConflictException for {}", updatedPersonDTO.toString());
            throw new ConflictException("Different country versions during update");
        }
        Country countryOfBirth = getCountryByIso(updatedPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = getCountryByIso(updatedPersonDTO.getCountryOfResidenceISO());
        Person result = PersonRepository.save(new Person(updatedPersonDTO, countryOfBirth, countryOfResidence));
        PersonDTO resultDTO = new PersonDTO(result);
        LOGGER.info("PersonService -> PUT -> update -> Updated {}", resultDTO.toString());
        return resultDTO;
    }

    public void delete(int id) throws DemoException {
        findById(id);
        PersonRepository.deleteById(id);
        LOGGER.info("PersonService -> DELETE -> delete -> Deleted person with id = {}", id);
    }

    private Country getCountryByIso(String iso) throws DemoException {
        if (iso == null) {
            LOGGER.error("PersonService -> getCountryByIso -> BadRequestException");
            throw new BadRequestException("Country ISO not provided");
        }
        List<Country> retrievedCountries = (List<Country>) CountryRepository.findByIso(iso);
        if (retrievedCountries.isEmpty()) {
            LOGGER.error("PersonService -> getCountryByIso -> NotFoundException for iso = {}", iso);
            throw new NotFoundException("Country not found for iso = " + iso);
        }
        return retrievedCountries.get(0);
    }
}