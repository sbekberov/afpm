package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.User;

import java.util.List;
import java.util.UUID;

@Component
public class UserRepository extends CRUDRepository<User> {

    private static final String CREATE_STMT = "INSERT INTO users(id, first_name, last_name, email, time_zone) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM users WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM users WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE users SET first_name=?, last_name=?, email=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM users";

    @Override
    public User findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(User.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User create(User entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getTimeZone());
        return findById(entity.getId());
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                UUID.randomUUID());
        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
