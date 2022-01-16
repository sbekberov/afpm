package spd.trello.repository;

import spd.trello.domain.CheckableItem;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CheckableItemRepository implements CRUDRepository<CheckableItem> {

    private final DataSource dataSource;

    public CheckableItemRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO checkable_item(id, name, checked, checklist_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM checkable_item WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM checkable_item WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE checkable_item SET  name=?, checked=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public CheckableItem findById(UUID id) throws IllegalAccessException {
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
    public List<CheckableItem> getAll() {
        try( Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_STMT);
            while (rs.next()) {
                CheckableItem checkableItem = new CheckableItem();
                checkableItem.setId(UUID.fromString(String.valueOf(rs.getString(1))));
                checkableItem.setName(rs.getString(2));
                checkableItem.setChecked(rs.getBoolean(3));
                checkableItem.setChecklistId(UUID.fromString(String.valueOf(rs.getString(4))));
                System.out.println(checkableItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Table CheckableItem is empty!");
    }

    @Override
    public CheckableItem create(CheckableItem entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setBoolean(3, entity.getChecked());
            statement.setObject(4, entity.getChecklistId());
            //use executeUpdate if no data will be returned (a non-SELECT operation).
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    @Override
    public CheckableItem update(CheckableItem entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, "Test");
            statement.setBoolean(4,Boolean.TRUE);
            //use valid id
            statement.setObject(5, UUID.randomUUID());
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
    private CheckableItem map(ResultSet rs) throws SQLException {
        CheckableItem checkableItem = new CheckableItem();
        checkableItem.setId(UUID.fromString(rs.getString("id")));
        checkableItem.setName(rs.getString("name"));
        checkableItem.setChecked(rs.getBoolean("checked"));
        checkableItem.setChecklistId(UUID.fromString(rs.getString("check_list_id")));
        return checkableItem;
    }

}
