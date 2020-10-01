package com.example.demo.endpoint;

import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int id) {
        Country result;
        try {
            result = CountryService.findById(id);
        } catch (CountryNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found", e);
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
        Country result;
        try {
            result = CountryService.update(id, newCountry);
        } catch (CountryNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country Not Found", e);
        } catch (UpdateIdMismatchException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad Request Body", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) {
        try {
            CountryService.delete(id);
        } catch (CountryNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@ExceptionHandler(CountryNotFoundException.class)
    public void handleNotFoundException(){

    }

    @ExceptionHandler(UpdateIdMismatchException.class)
    public void handleIdMismatchException(){

    }*/
}