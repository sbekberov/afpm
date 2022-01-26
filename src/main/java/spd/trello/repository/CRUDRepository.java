package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.db.DbConfiguration;

import java.util.List;
import java.util.UUID;
@Component
public abstract class CRUDRepository<E> {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DbConfiguration.getDataSource());
    public abstract E findById(UUID id);

    public abstract List<E> getAll();

    public abstract E create(E entity);

    public abstract E update(E entity);

    public abstract void delete(UUID id);
}

