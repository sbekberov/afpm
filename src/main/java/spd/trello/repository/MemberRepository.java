package spd.trello.repository;

import spd.trello.domain.Member;
import spd.trello.domain.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;

public class MemberRepository implements CRUDRepository<Member> {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO member (id, role, user_id, created_by, created_date) VALUES (?, ?, ?,?,?)";
    private static final String FIND_BY_STMT = "SELECT * FROM member WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM member WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE member SET  role=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM member";



    @Override
    public Member findById(UUID id)  {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error MemberRepository findById" , e);
        }
        throw new IllegalStateException("Member with ID: " + id.toString() + " doesn't exists");
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
            throw new IllegalStateException("Error MemberRepository getAll", e);
        }
        throw new IllegalStateException("Table Member is empty!");
    }

    @Override
    public Member create(Member entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, String.valueOf(entity.getRole()));
            statement.setObject(3, entity.getUsersId());
            statement.setString(4, entity.getCreatedBy());
            statement.setDate(5, entity.getCreatedDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error MemberRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public Member update(Member entity)  {
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
        } catch (SQLException | IllegalStateException e) {
            throw new IllegalStateException("Error MemberRepository update", e);
        }
        return findById(entity.getId());
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(DELETE_BY_STMT)) {
            statement.setObject(1, id);
            return  statement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new IllegalStateException("Error MemberRepository delete", e);
        }
    }


    private Member map(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(fromString(rs.getString("id")));
        member.setUpdatedBy(rs.getString("updated_by"));
        member.setCreatedBy(rs.getString("created_by"));
        member.setCreatedDate(rs.getDate("created_date"));
        member.setUpdatedDate(rs.getDate("updated_date"));
        member.setRole(Role.valueOf(rs.getString("role")));
        member.setUsersId(UUID.fromString(rs.getString("user_id")));
       // member.setWorkspaceId(UUID.fromString(rs.getString("workspace_id")));
        return member;
    }
}
