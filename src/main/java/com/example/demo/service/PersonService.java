package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
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

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    private final PersonRepository PersonRepository;
    private final CountryRepository CountryRepository;

    public PersonService(PersonRepository PersonRepository, CountryRepository CountryRepository) {
        this.PersonRepository = PersonRepository;
        this.CountryRepository = CountryRepository;
    }

    public List<PersonDTO> findAll() {
        logger.info("PersonService -> GET -> findAll");
        List<Person> result = PersonRepository.findAll();
        LinkedList<PersonDTO> resultDTO = new LinkedList<>();
        for (Person person : result) {
            resultDTO.add(new PersonDTO(person));
        }
        return resultDTO;
    }

    public PersonDTO findById(int id) throws DemoException {
        Optional<Person> result = PersonRepository.findById(id);
        if (result.isEmpty()) {
            logger.info("PersonService -> GET -> findById -> NotFoundException for id = " + id);
            throw new NotFoundException("Person not found");
        }
        logger.info("PersonService -> GET -> findById -> Successful for id = " + id);
        return new PersonDTO(result.get());
    }

    public PersonDTO create(PersonDTO newPersonDTO) throws DemoException {
        if (newPersonDTO.getCountryOfBirthISO() == null || newPersonDTO.getCountryOfResidenceISO() == null) {
            logger.info("PersonService -> POST -> create -> BadRequestException for " + newPersonDTO.toString());
            throw new BadRequestException("CountryOfBirthISO and/or CountryOfResidenceISO not provided");
        }
        List<Country> countryOfBirth = (List<Country>) CountryRepository.findByIso(newPersonDTO.getCountryOfBirthISO());
        if (countryOfBirth.isEmpty()) {
            logger.info("PersonService -> POST -> create -> NotFoundException for iso = " + newPersonDTO.getCountryOfBirthISO());
            throw new NotFoundException("Country not found for iso = " + newPersonDTO.getCountryOfBirthISO());
        }
        List<Country> countryOfResidence = (List<Country>) CountryRepository.findByIso(newPersonDTO.getCountryOfResidenceISO());
        if (countryOfResidence.isEmpty()) {
            logger.info("PersonService -> POST -> create -> NotFoundException for iso = " + newPersonDTO.getCountryOfResidenceISO());
            throw new NotFoundException("Country not found for iso = " + newPersonDTO.getCountryOfResidenceISO());
        }
        Person newPerson = new Person(newPersonDTO, countryOfBirth.get(0), countryOfResidence.get(0));
        logger.info("PersonService -> POST -> create -> " + newPersonDTO.toString());
        Person result = PersonRepository.save(newPerson);
        return new PersonDTO(result);
    }

    public PersonDTO update(int id, PersonDTO newPersonDTO) throws DemoException {
        if (newPerson.getId() != id && newPerson.getId() != 0) {
            logger.info("PersonService -> PUT -> update -> BadRequestException for path_id = " + id + " and body_id = " + newPerson.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if (!newPerson.getLastUpdateDate().equals(findById(id).getLastUpdateDate())) {
            logger.info("PersonService -> PUT -> update -> ConflictException for " + newPerson.toString());
            throw new ConflictException("Different person versions during update");
        }
        Person result = findById(id);
        result.setFullName(newPerson.getFullName());
        result.setSex(newPerson.getSex());
        result.setDateOfBirth(newPerson.getDateOfBirth());
        result.setCountryOfBirth(newPerson.getCountryOfBirth());
        result.setCountryOfResidence(newPerson.getCountryOfResidence());
        result.setTelephone(newPerson.getTelephone());
        result.setEmail(newPerson.getEmail());
        newPerson = PersonRepository.save(result);
        logger.info("PersonService -> PUT -> update -> Updated " + result.toString());
        return newPerson;
    }

    public void delete(int id) throws DemoException {
        findById(id);
        logger.info("PersonService -> POST -> delete -> Deleted person with id = " + id);
        PersonRepository.deleteById(id);
    }
}