package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Attachment;

import java.util.List;
import java.util.UUID;

@Component
public class AttachmentRepository extends CRUDRepository<Attachment> {



    private static final String CREATE_STMT = "INSERT INTO attachment(id, link, name, updated_by, created_by, created_date, updated_date, comment_id, card_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM attachment WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM attachment WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE attachment SET  link=?, name=?,updated_by=?, updated_date=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM attachment";

    @Override
    public Attachment findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Attachment.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Attachment> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Attachment.class));
    }

    @Override
    public Attachment create(Attachment entity) {
        jdbcTemplate.update(CREATE_STMT,

                entity.getId(),
                entity.getLink(),
                entity.getName(),
                entity.getUpdatedBy(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getCommentId(),
                entity.getCardId());
        return findById(entity.getId());
    }


    @Override
    public Attachment update(Attachment entity) {
        jdbcTemplate.update(UPDATE_BY_STMT,

                entity.getLink(),
                entity.getName(),
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                UUID.randomUUID());
        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
