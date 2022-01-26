package spd.trello.repository;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Workspace;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class WorkspaceRepository extends CRUDRepository<Workspace> {

    private final DataSource dataSource;

    public WorkspaceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO workspace(id, created_by, created_date, name, description) VALUES (?, ?, ?, ?,?)";
    private static final String FIND_BY_STMT = "SELECT * FROM workspace WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM workspace WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE workspace SET updated_by=? ,updated_date=?, name=?, description=?,workspace_visibility = ? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";


    @Override
    public List<Workspace> getAll() {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Workspace.class));
    }

    @Override
    public Workspace findById(UUID id) {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Workspace.class), id)
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
       // entity.setUpdatedBy(member.getCreatedBy());
        LocalDateTime updateDate = LocalDateTime.now();
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)) {
            statement.setString(1, entity.getUpdatedBy());
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getDescription());
            statement.setString(5, entity.getVisibility().toString());
            statement.setObject(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error WorkspaceRepository update", e);
        }
        return findById(entity.getId());
    }


    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
