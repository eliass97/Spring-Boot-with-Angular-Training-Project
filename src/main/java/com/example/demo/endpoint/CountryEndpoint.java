package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.http.HttpStatus;
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
    public List<Country> getAllCountries() {
        return CountryService.findAll();
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable("id") int id) throws DemoException {
        return CountryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country createCountry(@RequestBody Country newCountry) throws DemoException{
        return CountryService.create(newCountry);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable int id, @RequestBody Country updatedCountry) throws DemoException {
        return CountryService.update(id, updatedCountry);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable int id) throws DemoException {
        CountryService.delete(id);
    }
}