package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/country")
    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(0,"0","0","0"));
        countryList.add(new Country(1,"1","1","1"));
        countryList.add(new Country(2,"2","2","2"));
        return countryList;
    }

    @GetMapping("/country/{id}")
    public Country getCountryById(@PathVariable("id") int id) {
        return new Country(id,"2","0","0");
    }
}