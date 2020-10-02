package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.State;
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
    public Iterable<State> getAllCountries() {
        return (List<State>) CountryService.findAll();
    }

    @GetMapping("/{id}")
    public State getCountryById(@PathVariable("id") int id) throws DemoException {
        return CountryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State createCountry(@RequestBody State newState) {
        return CountryService.create(newState);
    }

    @PutMapping("/{id}")
    public State updateCountry(@PathVariable int id, @RequestBody State newState) throws DemoException {
        return CountryService.update(id, newState);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable int id) throws DemoException {
        CountryService.delete(id);
    }
}