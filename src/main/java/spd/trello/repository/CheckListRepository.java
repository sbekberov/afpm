package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Checklist;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CheckListRepository extends CRUDRepository<Checklist> {

    private static final String CREATE_STMT = "INSERT INTO checklist(id, name, updated_by, created_by, created_date, updated_date, card_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM checklist WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM checklist WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE checklist SET  updated_by=?, updated_date=?, name=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM checklist";

    @Override
    public Checklist findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Checklist.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Checklist> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Checklist.class));
    }

    @Override
    public Checklist create(Checklist entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getName(),
                entity.getUpdatedBy(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getCreatedDate(),
                entity.getCardId());

        return findById(entity.getId());
    }

    @Override
    public Checklist update(Checklist entity) {
       // entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getId());
        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
