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
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public User findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("Users with ID: " + id.toString() + " doesn't exists");
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
            throw new IllegalStateException("UserRepository::findAll failed", e);
        }
        throw new IllegalStateException("Table users is empty!");
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return entity;
    }

    @Override
    public User update(User entity) throws IllegalAccessException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_STMT)) {
            User oldUser = findById(entity.getId());
            if (entity.getFirstName() == null) {
                statement.setString(1, oldUser.getFirstName());
            } else {
                statement.setString(1, entity.getFirstName());
            }

            if (entity.getLastName() == null) {
                statement.setString(2, oldUser.getLastName());
            } else {
                statement.setString(2, entity.getLastName());
            }

            if (entity.getEmail() == null) {
                statement.setString(3, oldUser.getEmail());
            } else {
                statement.setString(3, entity.getEmail());
            }

            if (entity.getTimeZone() == null) {
                statement.setString(4, oldUser.getTimeZone());
            } else {
                statement.setString(4, entity.getTimeZone());
            }

            statement.setObject(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalStateException("User with ID: " + entity.getId().toString() + " doesn't updates");
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
    private User map(ResultSet rs) throws SQLException {
        User users = new User();
        users.setId(UUID.fromString(rs.getString("id")));
        users.setFirstName(rs.getString("firstname"));
        users.setFirstName(rs.getString("lastname"));
        users.setFirstName(rs.getString("email"));
        users.setTimeZone(rs.getString("time_zone"));
        return users;
    }
}
