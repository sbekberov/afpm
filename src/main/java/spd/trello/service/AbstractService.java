package spd.trello.service;


import spd.trello.domain.common.Resource;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.exception.BadRequestException;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<E extends Resource, R extends AbstractRepository<E>>
        implements CommonService<E>{
    R repository;

    public AbstractService(R repository){
        this.repository = repository;
    }

    @Override
    public E create(E entity) {
        try {
            return repository.save(entity);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public E update(E entity) {
        try {
            return repository.save(entity);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        }
        catch (RuntimeException e){
            throw new BadRequestException( e.getMessage());
        }
    }

    @Override
    public E findById(UUID id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }
}




