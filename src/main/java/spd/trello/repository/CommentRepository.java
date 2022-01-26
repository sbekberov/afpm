package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Comment;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CommentRepository extends CRUDRepository<Comment> {


    private static final String CREATE_STMT = "INSERT INTO comment(id, text, updated_by, created_by, created_date, updated_date, card_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM comment WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM comment WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE comment SET  updated_by=?, updated_date=?, text=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM comment";

    @Override
    public Comment findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Comment.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Comment> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public Comment create(Comment entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getContent(),
                entity.getUpdatedBy(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getUpdatedDate(),
                entity.getCardId(),
                entity.getUsersId());


        return findById(entity.getId());
    }

    @Override
    public Comment update(Comment entity) {
        //entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,

                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getContent(),
                entity.getId());
        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
