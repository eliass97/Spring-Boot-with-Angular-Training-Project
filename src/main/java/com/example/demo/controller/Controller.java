package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    CountryService service;

    @GetMapping("/country")
    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(-1, "s", "s", "s"));
        return countryList;
    }

    @GetMapping("/country/{id}")
    public Country getCountryById(@PathVariable("id") int id) {
        return service.findById(id);
    }
}