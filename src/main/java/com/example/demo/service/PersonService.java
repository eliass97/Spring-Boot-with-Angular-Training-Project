package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.model.Person;
import com.example.demo.model.PersonDTO;
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

    private final PersonRepository personRepository;
    private final CountryService countryService;

    public PersonService(PersonRepository personRepository, CountryService countryService) {
        this.personRepository = personRepository;
        this.countryService = countryService;
    }

    public List<PersonDTO> findAll() {
        Iterable<Person> result = personRepository.findAll();
        LinkedList<PersonDTO> resultDTO = new LinkedList<>();
        for (Person person : result) {
            resultDTO.add(new PersonDTO(person));
        }
        LOGGER.info("PersonService -> GET -> findAll -> Searched for all");
        return resultDTO;
    }

    public PersonDTO findByIdDTO(int id) throws DemoException {
        Person result = findById(id);
        LOGGER.info("PersonService -> GET -> findByIdDTO -> Searched for id = {}", id);
        return new PersonDTO(result);
    }

    private Person findById(int id) throws DemoException {
        Optional<Person> result = personRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.error("PersonService -> GET -> findById -> NotFoundException for id = {}", id);
            throw new NotFoundException("Person not found");
        }
        return result.get();
    }

    public PersonDTO create(PersonDTO newPersonDTO) throws DemoException {
        Country countryOfBirth = countryService.getCountryByIso(newPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = countryService.getCountryByIso(newPersonDTO.getCountryOfResidenceISO());
        Person newPerson = personRepository.save(new Person(newPersonDTO, countryOfBirth, countryOfResidence));
        LOGGER.info("PersonService -> POST -> create -> Created {}", newPerson);
        return new PersonDTO(newPerson);
    }

    public PersonDTO update(int pathId, PersonDTO updatedPersonDTO) throws DemoException {
        Person personToBeUpdated = findById(pathId);
        updateChecks(pathId, updatedPersonDTO, personToBeUpdated);
        Person updatedPerson = updateAndSaveInDatabase(updatedPersonDTO, personToBeUpdated);
        LOGGER.info("PersonService -> PUT -> update -> Updated {}", updatedPerson);
        return new PersonDTO(updatedPerson);
    }

    private void updateChecks(int pathId, PersonDTO updatedPersonDTO, Person personToBeUpdated) throws DemoException {
        if (updatedPersonDTO.getId() != pathId && updatedPersonDTO.getId() != 0) {
            LOGGER.error("PersonService -> basicUpdateChecks -> BadRequestException for path_id = {} and body_id = {}", pathId, updatedPersonDTO.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        personToBeUpdated.check(updatedPersonDTO.getLastUpdateDate());
        if (updatedPersonDTO.getCountryOfBirthISO() == null || updatedPersonDTO.getCountryOfResidenceISO() == null) {
            LOGGER.error("PersonService -> basicUpdateChecks -> BadRequestException");
            throw new BadRequestException("Country ISO not provided");
        }
    }

    private Person updateAndSaveInDatabase(PersonDTO updatedPersonDTO, Person personToBeUpdated) throws DemoException {
        Country countryOfBirth = countryService.getCountryByIso(updatedPersonDTO.getCountryOfBirthISO());
        Country countryOfResidence = countryService.getCountryByIso(updatedPersonDTO.getCountryOfResidenceISO());
        personToBeUpdated.setFullName(updatedPersonDTO.getFullName());
        personToBeUpdated.setSex(updatedPersonDTO.getSex());
        personToBeUpdated.setDateOfBirth(updatedPersonDTO.getDateOfBirth());
        personToBeUpdated.setCountryOfBirth(countryOfBirth);
        personToBeUpdated.setCountryOfResidence(countryOfResidence);
        personToBeUpdated.setTelephone(updatedPersonDTO.getTelephone());
        personToBeUpdated.setEmail(updatedPersonDTO.getEmail());
        return personRepository.save(personToBeUpdated);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        personRepository.deleteById(id);
        LOGGER.info("PersonService -> DELETE -> delete -> Deleted person with id = {}", id);
    }
}