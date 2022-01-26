package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.CardList;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CardListRepository extends CRUDRepository<CardList> {


    private static final String CREATE_STMT = "INSERT INTO card_list(id, board_id, created_by, created_date,  name, archived) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM card_list WHERE id=?";
    //  private static final String FIND_ALL_FOR_BOARD_STMT = "SELECT * FROM card_list WHERE board_id=?;";
    private static final String DELETE_BY_STMT = "DELETE FROM card_list WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE card_list SET  updated_by=?, updated_date=?, name=?, archived=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM card_list";

    @Override
    public CardList findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(CardList.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CardList> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(CardList.class));
    }

    @Override
    public CardList create(CardList entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getBoardId(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getName(),
                entity.getArchived());

        return findById(entity.getId());
    }

    @Override
    public CardList update(CardList entity) {
        // entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,

                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getArchived(),
                entity.getId());

        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }

}
