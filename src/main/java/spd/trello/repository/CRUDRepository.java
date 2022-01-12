package spd.trello.repository;

import java.util.List;
import java.util.UUID;

public interface CRUDRepository<E>{
    E findById(UUID id) throws IllegalAccessException;
    List<E> getAll();
    E create(E entity);
    E update(E entity) throws IllegalAccessException;
    boolean delete(UUID id);
}

