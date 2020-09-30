package com.example.demo.service;

import com.example.demo.repository.CountryRepository;
import com.example.demo.model.Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public Iterable<Country> findAll() {
        return repository.findAll();
    }

    public Country findById(int id) {
        return repository.findById(id);
    }
}