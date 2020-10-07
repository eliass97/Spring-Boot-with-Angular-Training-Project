package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.PersonDTO;
import com.example.demo.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonEndpoint {

    private final PersonService personService;

    public PersonEndpoint(PersonService PersonService) {
        this.personService = PersonService;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable("id") int id) throws DemoException {
        return personService.findByIdDTO(id);
    }

    @GetMapping("/page")
    public Page<PersonDTO> getPersonsByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sortBy", required = false) String sortBy, @RequestParam(name = "sortDirection", required = false) String sortDirection) throws DemoException {
        Sort.Direction sortDirectionEnum;
        if (sortDirection == null) {
            sortDirectionEnum = null;
        } else {
            sortDirectionEnum = Sort.Direction.valueOf(sortDirection.toUpperCase());
        }
        return personService.findPersonsByPage(page, size, sortBy, sortDirectionEnum);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO newPersonDTO) throws DemoException {
        return personService.create(newPersonDTO);
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable int id, @RequestBody PersonDTO updatedPersonDTO) throws DemoException {
        return personService.update(id, updatedPersonDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable int id) throws DemoException {
        personService.delete(id);
    }
}