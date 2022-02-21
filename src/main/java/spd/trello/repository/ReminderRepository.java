package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Reminder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class ReminderRepository extends CRUDRepository<Reminder> {


    private static final String CREATE_STMT = "INSERT INTO reminder(id, start, finish, remind_on, active,  created_by, created_date,card_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM reminder WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM reminder WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE reminder SET start=?, finish=?, remind_on=?, active=?,updated_date=?,updated_by=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM reminder";

    @Override
    public Reminder findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Reminder.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Reminder> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Reminder.class));
    }

    @Override
    public Reminder create(Reminder entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getStart(),
                entity.getFinish(),
                entity.getRemindOn(),
                entity.getActive(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getCardId());

        return findById(entity.getId());
    }

    @Override
    public Reminder update(Reminder entity) {
       // entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getStart(),
                entity.getFinish(),
                entity.getRemindOn(),
                entity.getActive(),
                entity.getUpdatedDate(),
                entity.getUpdatedBy(),
                entity.getId());


        return findById(entity.getId());
    }


    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
