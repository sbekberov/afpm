package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.CheckList;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CheckListRepository extends CRUDRepository<CheckList> {

    private static final String CREATE_STMT = "INSERT INTO checklist(id, name,  created_by, created_date,  card_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM checklist WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM checklist WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE checklist SET  updated_by=?, updated_date=?, name=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM checklist";

    @Override
    public CheckList findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(CheckList.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CheckList> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(CheckList.class));
    }

    @Override
    public CheckList create(CheckList entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getName(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getCardId());

        return findById(entity.getId());
    }

    @Override
    public CheckList update(CheckList entity) {
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
