package com.example.demo.service;

import com.example.demo.repository.CountryRepository;
import com.example.demo.model.Country;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryService {

    @Autowired
    private CountryRepository repository;

    public Country findById(int id) {
        return repository.findById(id);
    }
}