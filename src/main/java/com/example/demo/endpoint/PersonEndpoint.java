package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.PersonDTO;
import com.example.demo.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonEndpoint {

    private final PersonService PersonService;

    public PersonEndpoint(PersonService PersonService) {
        this.PersonService = PersonService;
    }

    @GetMapping
    public List<PersonDTO> getAllPeople() {
        return PersonService.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable("id") int id) throws DemoException {
        return PersonService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO newPersonDTO) throws DemoException {
        return PersonService.create(newPersonDTO);
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable int id, @RequestBody PersonDTO updatedPersonDTO) throws DemoException {
        return PersonService.update(id, updatedPersonDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable int id) throws DemoException {
        PersonService.delete(id);
    }
}