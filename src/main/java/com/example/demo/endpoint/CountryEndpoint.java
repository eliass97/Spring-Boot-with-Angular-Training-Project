package com.example.demo.endpoint;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
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
    public Iterable<Country> getAllCountries() {
        List<Country> countryList = (List<Country>) CountryService.findAll();
        if (countryList.isEmpty()) {
            countryList.add(new Country(-1, "UNK", "Country not found", "UNK")); //404 not found
        }
        return countryList;
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable("id") int id) {
        Country foundCountry = CountryService.findById(id);
        if (foundCountry == null) {
            return new Country(-1, "UNK", "Country not found", "UNK"); //404 not found
        }
        return foundCountry;
    }

    @PostMapping
    public Country createCountry(@RequestBody Country newCountry) {
        Country createdCountry = CountryService.createCountry(newCountry);
        if (createdCountry == null) {
            return new Country(-1, "UNK", "Not acceptable body", "UNK"); //201 no good body
        }
        return createdCountry;
    }

    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable int id, @RequestBody Country newCountry) {
        if (newCountry.getId() != id) {
            return new Country(-1, "UNK", "The IDs don't match", "UNK"); //400 no id match
        }
        if (CountryService.findById(id) == null) {
            return new Country(-1, "UNK", "Country not found", "UNK"); //404 not found
        }
        return CountryService.updateCountry(id, newCountry);
    }

    @DeleteMapping("/{id}")
    public Country deleteCountry(@PathVariable int id) {
        Country deletedCountry = CountryService.findById(id);
        if (deletedCountry == null) {
            return new Country(-1, "UNK", "Country not found", "UNK"); //404 not found
        }
        CountryService.deleteCountry(id);
        return deletedCountry;
    }
}