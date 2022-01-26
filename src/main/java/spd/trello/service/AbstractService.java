package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Domain;
import spd.trello.repository.CRUDRepository;

import java.util.List;
import java.util.UUID;

@Service
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

    public T update(T entity) {
        return repository.update(entity);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

    public List<T> getAll() {
        return repository.getAll();
    }
}
