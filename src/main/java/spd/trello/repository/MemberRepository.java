package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Member;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class MemberRepository extends CRUDRepository<Member> {


    private static final String CREATE_STMT = "INSERT INTO member (id, role, user_id, created_by, created_date) VALUES (?, ?, ?,?,?)";
    private static final String FIND_BY_STMT = "SELECT * FROM member WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM member WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE member SET  role=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM member";


    @Override
    public Member findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_STMT, new BeanPropertyRowMapper<>(Member.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Member> getAll() {
        return jdbcTemplate.query(GET_ALL_STMT, new BeanPropertyRowMapper<>(Member.class));
    }

    @Override
    public Member create(Member entity) {
        jdbcTemplate.update(CREATE_STMT,
                entity.getId(),
                entity.getRole().toString(),
                entity.getUsersId(),
                entity.getCreatedBy(),
                entity.getCreatedDate());

        return findById(entity.getId());
    }

    @Override
    public Member update(Member entity) {
        //entity.setUpdatedBy(user.getEmail());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        jdbcTemplate.update(UPDATE_BY_STMT,
                entity.getUpdatedBy(),
                entity.getUpdatedDate(),
                entity.getRole().toString(),
                entity.getId());

        return findById(entity.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(DELETE_BY_STMT, id);
    }


}
