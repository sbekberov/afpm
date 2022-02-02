package spd.trello.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public abstract class CRUDRepository<E> {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public abstract E findById(UUID id);

    public abstract List<E> getAll();

    public abstract E create(E entity);

    public abstract E update(E entity);

    public abstract void delete(UUID id);
}

