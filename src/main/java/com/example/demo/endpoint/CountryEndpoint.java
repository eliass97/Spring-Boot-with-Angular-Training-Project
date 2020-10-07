package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(CountryService CountryService) {
        this.countryService = CountryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable("countries")
    public Country getCountryById(@PathVariable("id") int id) throws DemoException {
        return countryService.findById(id);
    }

    @GetMapping("/page")
    public Page<Country> getCountriesByPage(Pageable pageableRequest) {
        return countryService.findCountriesByPage(pageableRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country createCountry(@RequestBody Country newCountry) throws DemoException {
        return countryService.create(newCountry);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable int id, @RequestBody Country updatedCountry) throws DemoException {
        return countryService.update(id, updatedCountry);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable int id) throws DemoException {
        countryService.delete(id);
    }
}