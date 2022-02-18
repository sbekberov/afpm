package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.common.Domain;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.service.AbstractService;
import spd.trello.service.CommonService;

import java.util.List;
import java.util.UUID;

public class AbstractController< E extends Domain, S extends CommonService<E>>
        implements CommonController<E> {
    S service;


    public AbstractController(S service) {
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<E> create(@RequestBody E resource) {
        E result = service.create(resource);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Override
    public ResponseEntity<E> update(@PathVariable UUID id, @RequestBody E resource) {
        E entity = service.findById(id);
        if (entity == null) throw new ResourceNotFoundException();
        resource.setId(id);
        E result = service.update(resource);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Override
    public HttpStatus delete(@PathVariable UUID id) {
        service.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<E> findById(@PathVariable UUID id) {
        E result = service.findById(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @GetMapping
    @Override
    public List<E> getAll() {
        return service.getAll();
    }
}
