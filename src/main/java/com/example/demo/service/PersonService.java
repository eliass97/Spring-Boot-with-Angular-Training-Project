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

    public PersonDTO findByIdDTO(int id) throws DemoException {
        Person result = findById(id);
        LOGGER.info("PersonService -> GET -> findById -> Searched for id = {}", id);
        return new PersonDTO(result);
    }

    private Person findById(int id) throws DemoException {
        Optional<Person> result = PersonRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.error("PersonService -> GET -> findById -> NotFoundException for id = {}", id);
            throw new NotFoundException("Person not found");
        }
        return result.get();
    }

    public PersonDTO create(PersonDTO newPersonDTO) throws DemoException {
        Country countryOfBirth = getCountryByIso(newPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = getCountryByIso(newPersonDTO.getCountryOfResidenceISO());
        Person newPerson = new Person(newPersonDTO, countryOfBirth, countryOfResidence);
        Person result = PersonRepository.save(newPerson);
        LOGGER.info("PersonService -> POST -> create -> Created {}", newPersonDTO);
        return new PersonDTO(result);
    }

    private Country getCountryByIso(String iso) throws DemoException {
        if (iso == null) {
            LOGGER.error("PersonService -> getCountryByIso -> BadRequestException");
            throw new BadRequestException("Country ISO not provided");
        }
        List<Country> result = (List<Country>) CountryRepository.findByIso(iso);
        if (result.isEmpty()) {
            LOGGER.error("PersonService -> getCountryByIso -> NotFoundException for iso = {}", iso);
            throw new NotFoundException("Country not found for iso = " + iso);
        }
        return result.get(0);
    }

    public PersonDTO update(int pathId, PersonDTO updatedPersonDTO) throws DemoException {
        Person personToBeUpdated = findById(pathId);
        updateChecks(pathId, updatedPersonDTO, personToBeUpdated);
        Person result = updateAndSaveInDatabase(updatedPersonDTO, personToBeUpdated);
        PersonDTO resultDTO = new PersonDTO(result);
        LOGGER.info("PersonService -> PUT -> update -> Updated {}", resultDTO);
        return resultDTO;
    }

    private void updateChecks(int pathId, PersonDTO updatedPersonDTO, Person personToBeUpdated) throws DemoException {
        if (updatedPersonDTO.getId() != pathId && updatedPersonDTO.getId() != 0) {
            LOGGER.error("PersonService -> basicUpdateChecks -> BadRequestException for path_id = {} and body_id = {}", pathId, updatedPersonDTO.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if (updatedPersonDTO.getLastUpdateDate() == null) {
            LOGGER.error("CountryService -> PUT -> basicUpdateChecks -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        if (!updatedPersonDTO.getLastUpdateDate().equals(personToBeUpdated.getLastUpdateDate())) {
            LOGGER.error("PersonService -> update -> ConflictException for {}", updatedPersonDTO);
            throw new ConflictException("Different country versions during update");
        }
    }

    private Person updateAndSaveInDatabase(PersonDTO updatedPersonDTO, Person personToBeUpdated) throws DemoException {
        Country countryOfBirth = getCountryByIso(updatedPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = getCountryByIso(updatedPersonDTO.getCountryOfResidenceISO());
        personToBeUpdated.setFullName(updatedPersonDTO.getFullName());
        personToBeUpdated.setSex(updatedPersonDTO.getSex());
        personToBeUpdated.setDateOfBirth(updatedPersonDTO.getDateOfBirth());
        personToBeUpdated.setCountryOfBirth(countryOfBirth);
        personToBeUpdated.setCountryOfResidence(countryOfResidence);
        personToBeUpdated.setTelephone(updatedPersonDTO.getTelephone());
        personToBeUpdated.setEmail(updatedPersonDTO.getEmail());
        return PersonRepository.save(personToBeUpdated);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        PersonRepository.deleteById(id);
        LOGGER.info("PersonService -> DELETE -> delete -> Deleted person with id = {}", id);
    }
}