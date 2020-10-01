package com.example.demo.endpoint;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryEndpoint {

    private final CountryService CountryService;

    public CountryEndpoint(CountryService CountryService) {
        this.CountryService = CountryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Country>> getAllCountries() {
        List<Country> result = (List<Country>) CountryService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int id) throws ResourceNotFoundException {
        Country result;
        result = CountryService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country newCountry) {
        Country result = CountryService.create(newCountry);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable int id, @RequestBody Country newCountry) throws ResourceNotFoundException, UpdateIdMismatchException {
        Country result;
        result = CountryService.update(id, newCountry);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) throws ResourceNotFoundException {
        CountryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}