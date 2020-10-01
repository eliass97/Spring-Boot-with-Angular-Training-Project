package com.example.demo.endpoint;

import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int id) throws CountryNotFoundException {
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
    public ResponseEntity<Country> updateCountry(@PathVariable int id, @RequestBody Country newCountry) throws CountryNotFoundException, UpdateIdMismatchException {
        Country result;
        result = CountryService.update(id, newCountry);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) throws CountryNotFoundException {
        CountryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Country not found")
    @ExceptionHandler(CountryNotFoundException.class)
    public void handleNotFoundException() {

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Mismatch between parameter and body id")
    @ExceptionHandler(UpdateIdMismatchException.class)
    public void handleIdMismatchException() {

    }
}