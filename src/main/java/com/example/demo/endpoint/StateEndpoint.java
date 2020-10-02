package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.model.State;
import com.example.demo.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/state")
public class StateEndpoint {

    private final StateService StateService;

    public StateEndpoint(StateService StateService) {
        this.StateService = StateService;
    }

    @GetMapping
    public Iterable<State> getAllStates() {
        return (List<State>) StateService.findAll();
    }

    @GetMapping("/{id}")
    public State getStateById(@PathVariable("id") int id) throws DemoException {
        return StateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State createState(@RequestBody State newState) {
        return StateService.create(newState);
    }

    @PutMapping("/{id}")
    public State updateState(@PathVariable int id, @RequestBody State newState) throws DemoException {
        return StateService.update(id, newState);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable int id) throws DemoException {
        StateService.delete(id);
    }
}