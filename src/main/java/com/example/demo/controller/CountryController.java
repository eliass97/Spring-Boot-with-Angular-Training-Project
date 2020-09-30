package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountryController {

    @Autowired
    CountryService service;

    @GetMapping("/country")
    public Iterable<Country> getAllCountries() {
        return service.findAll();
    }

    @GetMapping("/country/{id}")
    public Country getCountryById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping("/country")
    public Country createCountry(@RequestBody Country newCountry) {
        return service.createCountry(newCountry);
    }

    @PutMapping("/country/{id}")
    public Country updateCountry(@RequestBody Country newCountry, @PathVariable int id) {
        return service.updateCountry(newCountry, id);
    }

    @DeleteMapping("/country/{id}")
    public void deleteCountry(@PathVariable int id) {
        service.deleteCountry(id);
    }
}