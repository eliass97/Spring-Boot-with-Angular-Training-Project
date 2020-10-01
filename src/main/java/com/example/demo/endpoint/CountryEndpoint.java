package com.example.demo.endpoint;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryEndpoint {

    private final CountryService CountryService;

    public CountryEndpoint(CountryService CountryService) {
        this.CountryService = CountryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Country>> getAllCountries() {
        List<Country> result = (List<Country>) CountryService.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int id) {
        Country result = CountryService.findById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country newCountry) {
        Country result = CountryService.create(newCountry);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable int id, @RequestBody Country newCountry) {
        if (newCountry.getId() != id && newCountry.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (CountryService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Country result = CountryService.update(id, newCountry);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) {
        Country deletedCountry = CountryService.findById(id);
        if (CountryService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CountryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}