package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.State;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private final StateRepository StateRepository;
    private final CountryRepository CountryRepository;

    public CountryService(CountryRepository CountryRepository, StateRepository StateRepository) {
        this.StateRepository = StateRepository;
        this.CountryRepository = CountryRepository;
    }

    public Iterable<State> findAll() {
        return CountryRepository.findAll();
    }

    public State findById(int id) throws DemoException {
        Optional<State> result = CountryRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Country not found");
        }
        return result.get();
    }

    public State create(State newState) {
        return CountryRepository.save(newState);
    }

    public State update(int id, State newState) throws DemoException {
        if (newState.getId() != id && newState.getId() != 0) {
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        findById(id);
        newState.setId(id);
        return CountryRepository.save(newState);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        CountryRepository.deleteById(id);
    }
}