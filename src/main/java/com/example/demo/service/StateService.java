package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.State;
import com.example.demo.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateService {

    private final StateRepository StateRepository;
    private final CountryService CountryService;

    public StateService(StateRepository StateRepository, CountryService CountryService) {
        this.StateRepository = StateRepository;
        this.CountryService = CountryService;
    }

    public Iterable<State> findAll() {
        return StateRepository.findAll();
    }

    public State findById(int id) throws DemoException {
        Optional<State> result = StateRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("State not found");
        }
        return result.get();
    }

    public State create(State newState) {
        return StateRepository.save(newState);
    }

    public State update(int id, State newState) throws DemoException {
        if (newState.getId() != id && newState.getId() != 0) {
            throw new BadRequestException("Path ID variable does not match with body ID");
        }
        findById(id);
        try {
            CountryService.findById(newState.getCountry_id());
        } catch (DemoException e) {
            throw new BadRequestException("Provided country_id does not match with any of the existing countries");
        }
        newState.setId(id);
        return StateRepository.save(newState);
    }

    public void delete(int id) throws DemoException {
        findById(id);
        StateRepository.deleteById(id);
    }
}