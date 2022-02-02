package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.Resource;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.service.AbstractService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbstractController< E extends Resource, S extends AbstractService<E>>
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
        resource.setCreatedBy("s.bekberov@gmail.com");
        resource.setUpdatedBy("bekberov.selim@gmail.com");
        resource.setCreatedDate(Date.valueOf(LocalDate.now()));
        resource.setUpdatedDate(Date.valueOf(LocalDate.now()));
        E result = service.update(id, resource);
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
