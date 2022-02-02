package spd.trello.repository;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Workspace;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class WorkspaceRepository extends CRUDRepository<Workspace> {


    private static final String CREATE_STMT = "INSERT INTO workspace(id, created_by, created_date, name, description) VALUES (?, ?, ?, ?,?)";
    private static final String FIND_BY_STMT = "SELECT * FROM workspace WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM workspace WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE workspace SET updated_by=? ,updated_date=?, name=?, description=?,workspace_visibility = ? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";


    @Override
    public List<Workspace> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Workspace.class));
    }

    @Override
    public Workspace findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Workspace.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Workspace create(Workspace entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getName(),
                entity.getDescription());

        return findById(entity.getId());
    }

    @Override
    public Workspace update(Workspace entity) {
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getDescription(),
                entity.getVisibility().toString(),
                entity.getId());
        return findById(entity.getId());
    }


    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
