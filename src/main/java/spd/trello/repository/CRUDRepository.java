package spd.trello.repository;

import java.util.List;
import java.util.UUID;

public interface CRUDRepository<E>{
    E findById(UUID id) ;
    List<E> getAll();
    E create(E entity);
    E update(E entity);
    boolean delete(UUID id);
}

