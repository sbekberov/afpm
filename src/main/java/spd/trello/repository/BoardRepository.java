package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Board;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class BoardRepository extends CRUDRepository<Board> {


    private static final String CREATE_STMT = "INSERT INTO board(id, workspace_id,  created_by, created_date,  name, archived, visibility, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM board WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM board WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE board SET updated_by=? ,updated_date=?, name=?, description=?, visibility=?,archived=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM board";


    @Override
    public Board findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Board.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Board create(Board entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getWorkspaceId(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getName(),
                entity.getArchived(),
                entity.getVisibility().toString(),
                entity.getDescription());

        return findById(entity.getId());
    }


    @Override
    public Board update(Board entity) {
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getDescription(),
                entity.getVisibility().toString(),
                entity.getArchived(),
                entity.getId());
        return findById(entity.getId());
    }


    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }

    @Override
    public List<Board> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Board.class));
    }


}
