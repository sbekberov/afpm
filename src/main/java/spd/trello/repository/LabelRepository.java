package spd.trello.repository;

import spd.trello.domain.Label;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class LabelRepository implements CRUDRepository<Label>{

    private final DataSource dataSource;
    public LabelRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO label(id, name) VALUES (?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM label WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM comment WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE label SET  name=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public Label findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("CheckableItem with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Label> getAll() {
        try( Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_STMT);
            while (rs.next()) {
                Label label = new Label();
                label.setId(UUID.fromString(String.valueOf(rs.getString(1))));
                label.setName(rs.getString(7));
                System.out.println(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Table Label is empty!");
    }

    @Override
    public Label create(Label entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
         return entity;
    }

    @Override
    public Label update(Label entity) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, "Test");
            statement.setObject(2, UUID.randomUUID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
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
    private Label map(ResultSet rs) throws SQLException {
        Label label = new Label();
        label.setId(UUID.fromString(rs.getString("id")));
        label.setName(rs.getString("name"));
        return label;
    }
}
