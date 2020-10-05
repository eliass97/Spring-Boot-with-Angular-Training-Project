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

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

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
        logger.info("PersonService -> GET -> findAll -> Searched for all");
        return resultDTO;
    }

    public PersonDTO findById(int id) throws DemoException {
        Optional<Person> result = PersonRepository.findById(id);
        if (result.isEmpty()) {
            logger.info("PersonService -> GET -> findById -> NotFoundException for id = " + id);
            throw new NotFoundException("Person not found");
        }
        logger.info("PersonService -> GET -> findById -> Searched for id = " + id);
        return new PersonDTO(result.get());
    }

    public PersonDTO create(PersonDTO newPersonDTO) throws DemoException {
        List<Country> countriesOfBirthAndResidence = getBirthResidenceCountries(newPersonDTO.getCountryOfBirthISO(), newPersonDTO.getCountryOfResidenceISO());
        Person newPerson = new Person(newPersonDTO, countriesOfBirthAndResidence.get(0), countriesOfBirthAndResidence.get(0));
        Person result = PersonRepository.save(newPerson);
        logger.info("PersonService -> POST -> create -> Created " + newPersonDTO.toString());
        return new PersonDTO(result);
    }

    public PersonDTO update(int id, PersonDTO newPersonDTO) throws DemoException {
        updateChecks(id, newPersonDTO);
        List<Country> countriesOfBirthAndResidence = getBirthResidenceCountries(newPersonDTO.getCountryOfBirthISO(), newPersonDTO.getCountryOfResidenceISO());
        PersonDTO resultDTO = findById(id);
        resultDTO.setFullName(newPersonDTO.getFullName());
        resultDTO.setSex(newPersonDTO.getSex());
        resultDTO.setDateOfBirth(newPersonDTO.getDateOfBirth());
        resultDTO.setCountryOfBirthISO(newPersonDTO.getCountryOfBirthISO());
        resultDTO.setCountryOfResidenceISO(newPersonDTO.getCountryOfResidenceISO());
        resultDTO.setTelephone(newPersonDTO.getTelephone());
        resultDTO.setEmail(newPersonDTO.getEmail());
        resultDTO.setLastUpdateDate(newPersonDTO.getLastUpdateDate());
        Person newPerson = new Person(resultDTO, countriesOfBirthAndResidence.get(0), countriesOfBirthAndResidence.get(1));
        Person result = PersonRepository.save(newPerson);
        logger.info("PersonService -> PUT -> update -> Updated " + resultDTO.toString());
        return new PersonDTO(result);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        PersonRepository.deleteById(id);
        logger.info("PersonService -> POST -> delete -> Deleted person with id = " + id);
    }

    private void updateChecks(int path_id, PersonDTO newPersonDTO) throws DemoException {
        if (newPersonDTO.getId() != path_id && newPersonDTO.getId() != 0) {
            logger.info("PersonService -> PUT -> update -> BadRequestException for path_id = " + path_id + " and body_id = " + newPersonDTO.getId());
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        if (!newPersonDTO.getLastUpdateDate().equals(findById(path_id).getLastUpdateDate())) {
            logger.info("PersonService -> PUT -> update -> ConflictException for " + newPersonDTO.toString());
            throw new ConflictException("Different country versions during update");
        }
    }

    private List<Country> getBirthResidenceCountries(String countryOfBirthISO, String countryOfResidenceISO) throws DemoException {
        if (countryOfBirthISO == null || countryOfResidenceISO == null) {
            logger.info("PersonService -> POST -> create -> BadRequestException for not provided iso");
            throw new BadRequestException("CountryOfBirthISO and/or CountryOfResidenceISO not provided");
        }
        List<Country> countryOfBirth = (List<Country>) CountryRepository.findByIso(countryOfBirthISO);
        if (countryOfBirth.isEmpty()) {
            logger.info("PersonService -> POST -> create -> NotFoundException for iso = " + countryOfBirthISO);
            throw new NotFoundException("Country not found for iso = " + countryOfBirthISO);
        }
        List<Country> countryOfResidence = (List<Country>) CountryRepository.findByIso(countryOfResidenceISO);
        if (countryOfResidence.isEmpty()) {
            logger.info("PersonService -> POST -> create -> NotFoundException for iso = " + countryOfResidenceISO);
            throw new NotFoundException("Country not found for iso = " + countryOfResidenceISO);
        }
        List<Country> result = new LinkedList<>();
        result.add(countryOfBirth.get(0));
        result.add(countryOfResidence.get(0));
        return result;
    }
}