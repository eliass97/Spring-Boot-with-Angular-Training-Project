package com.example.demo.endpoint;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/country")
public class CountryEndpoint {

    private final CountryService CountryService;

    public CountryEndpoint(CountryService CountryService) {
        this.CountryService = CountryService;
    }

    @GetMapping
    public Iterable<Country> getAllCountries() {
        return CountryService.findAll();
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable("id") int id) {
        return CountryService.findById(id);
    }

    @PostMapping
    public Country createCountry(@RequestBody Country newCountry) {
        return CountryService.createCountry(newCountry);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable int id, @RequestBody Country newCountry) {
        return CountryService.updateCountry(newCountry, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable int id) {
        CountryService.deleteCountry(id);
    }
}