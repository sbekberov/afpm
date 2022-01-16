package spd.trello.repository;

import spd.trello.domain.Member;
import spd.trello.domain.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemberRepository implements CRUDRepository<Member> {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO member(id, role, user_id, workspace_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM member WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM member WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE member SET  role=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";



    @Override
    public Member findById(UUID id) throws IllegalAccessException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalAccessException("Member with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Member> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)) {
            List<Member> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("MemberRepository::findAll failed", e);
        }
        throw new IllegalStateException("Table members is empty!");
    }

    @Override
    public Member create(Member entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, String.valueOf(entity.getRole()));
            statement.setObject(3, entity.getUsersId());
            statement.setObject(4,entity.getWorkspaceId());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return entity;
    }

    @Override
    public Member update(Member entity) throws IllegalAccessException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_STMT)) {
            Member oldMember = findById(entity.getId());
            statement.setString(1, entity.getUpdatedBy());
            statement.setDate(2, entity.getUpdatedDate());
            if (entity.getRole() == null) {
                statement.setString(3, oldMember.getRole().toString());
            } else {
                statement.setString(3, entity.getRole().toString());
            }
            statement.setObject(4, entity.getId());
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalStateException("Member with ID: " + entity.getId().toString() + " doesn't updates");
        }
        return findById(entity.getId());
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(DELETE_BY_STMT)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Member map(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(UUID.fromString(rs.getString("id")));
        member.setRole(Role.valueOf(rs.getString("role")));
        member.setUsersId(UUID.fromString(String.valueOf(rs.getString("user_id"))));
        member.setWorkspaceId(UUID.fromString(String.valueOf(rs.getString("workspace_id"))));
        return member;
    }
}
