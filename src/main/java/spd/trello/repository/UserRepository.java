package spd.trello.repository;

import spd.trello.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements CRUDRepository<User>{

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO users(id, first_name, last_name, email, time_zone) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM users WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM users WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE users SET first_name=?, last_name=?, email=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM users";

    @Override
    public User findById(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error UserRepository findById" , e);
        }
        throw new IllegalStateException("Users with ID: " + id.toString() + " doesn't exists");
    }

   @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)) {
            List<User> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if(!result.isEmpty()){
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error UserRepository getAll", e);
        }
        throw new IllegalStateException("Table User is empty!");
    }

    @Override
    public User create(User entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getTimeZone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error UserRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public User update(User entity) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setObject(4, UUID.randomUUID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error UserRepository update", e);
        }
        return findById(entity.getId());
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(DELETE_BY_STMT)) {
            statement.setObject(1, id);
            return statement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new IllegalStateException("Error UserRepository delete");
        }
    }
    private User map(ResultSet rs) throws SQLException {
        User users = new User();
        users.setId(UUID.fromString(rs.getString("id")));
        users.setFirstName(rs.getString("first_name"));
        users.setLastName(rs.getString("last_name"));
        users.setEmail(rs.getString("email"));
        users.setTimeZone(rs.getString("time_zone"));
        return users;
    }
}
