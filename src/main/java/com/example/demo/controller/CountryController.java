package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}