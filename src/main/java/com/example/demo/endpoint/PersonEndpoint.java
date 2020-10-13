package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.PersonDTO;
import com.example.demo.service.PersonService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonEndpoint {

    private final PersonService personService;

    public PersonEndpoint(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable("persons")
    public PersonDTO getPersonById(@PathVariable("id") int id) throws DemoException {
        return personService.findById(id);
    }

    @GetMapping("/page")
    public Page<PersonDTO> getPersonsByPage(Pageable pageableRequest) {
        return personService.findPersonsByPage(pageableRequest);
    }

    @PostMapping
    @CachePut(value = "persons", key = "#newPersonDTO.id")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO newPersonDTO) throws DemoException {
        return personService.create(newPersonDTO);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "persons", key = "#id")
    public PersonDTO updatePerson(@PathVariable("id") int id, @RequestBody PersonDTO updatedPersonDTO) throws DemoException {
        return personService.update(id, updatedPersonDTO);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "persons", key = "#id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("id") int id) throws DemoException {
        personService.delete(id);
    }
}