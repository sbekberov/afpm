package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Card;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CardRepository extends CRUDRepository<Card> {




    private static final String CREATE_STMT = "INSERT INTO card(id, cardlist_id, updated_by, created_by, created_date, updated_date, name, archived, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM card WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM card WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE card SET  updated_by=?, updated_date=?, name=?, archived=?, description=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM card";

    @Override
    public Card findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Card.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Card> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Card.class));
    }

    @Override
    public Card create(Card entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getCardListId(),
                entity.getUpdatedBy(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getArchived(),
                entity.getDescription());

        return findById(entity.getId());
    }

    @Override
    public Card update(Card entity) {
        //entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getName(),
                entity.getArchived(),
                entity.getDescription(),
                entity.getId());

        return findById(entity.getId());
    }


    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
