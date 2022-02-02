package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spd.trello.domain.Resource;

import java.util.List;
import java.util.UUID;

public interface CommonController<E extends Resource> {

    ResponseEntity<E> create(E resource);
    ResponseEntity<E> update(UUID id, E resource);
    HttpStatus delete(UUID id);
    ResponseEntity<E> readById(UUID id);
    List<E> readAll();
}

