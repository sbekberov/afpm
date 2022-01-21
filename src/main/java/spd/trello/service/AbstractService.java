package spd.trello.service;

import spd.trello.domain.Domain;
import spd.trello.repository.CRUDRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T extends Domain> {

    protected CRUDRepository<T> repository;

    public AbstractService(CRUDRepository<T> repository) {
        this.repository = repository;
    }

    public T findById(UUID id) {
        return repository.findById(id);
    }

    public T create(T entity) {
        repository.create(entity);
        return repository.findById(entity.getId());
    }

    public T update(UUID id, T entity) {
        return repository.update(entity);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }

    public List<T> getAll() {
        return repository.getAll();
    }
}
