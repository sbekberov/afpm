package spd.trello.repository;

import spd.trello.domain.Checklist;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckListRepository implements CRUDRepository<Checklist>{

    private static DataSource dataSource;


    private static final String CREATE_STMT = "INSERT INTO checklist(id, name, updated_by, created_by, created_date, updated_date, card_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM checklist WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM checklist WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE checklist SET  updated_by=?, updated_date=?, name=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public Checklist findById(UUID id) throws IllegalAccessException {
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
    public List<Checklist> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)){
            List<Checklist> result = new ArrayList<>();
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()){
                result.add(map(resultSet));
            }
            if(!result.isEmpty()){
                return result;
            }
        }catch (SQLException e){
            throw new IllegalStateException("AttachmentRepository", e);
        }
        throw new IllegalStateException("Table Attachment is empty");
    }

    @Override
    public Checklist create(Checklist entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getUpdatedBy());
            statement.setString(4, entity.getCreatedBy());
            statement.setDate(5, entity.getCreatedDate());
            statement.setDate(6, entity.getCreatedDate());
            statement.setObject(7, entity.getCardId());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return entity;
    }

    @Override
    public Checklist update(Checklist entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, "Test");
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, "Test");
            statement.setObject(4, UUID.fromString("6fa408e2-e42d-4ad3-9233-87691323acec"));
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
    private Checklist map(ResultSet rs) throws SQLException {
        Checklist checklist = new Checklist();
        checklist.setId(UUID.fromString(rs.getString("id")));
        checklist.setName(rs.getString("name"));
        checklist.setUpdatedBy(rs.getString("updated_by"));
        checklist.setCreatedBy(rs.getString("created_by"));
        checklist.setCreatedDate(rs.getDate("created_date"));
        checklist.setUpdatedDate((rs.getDate("updated_date")));
        checklist.setCardId(UUID.fromString(rs.getString("card_id")));
        return checklist;
    }
}
