package spd.trello.repository;

import spd.trello.domain.Reminder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReminderRepository implements CRUDRepository<Reminder>{

    private static DataSource dataSource;


    private static final String CREATE_STMT = "INSERT INTO reminder(id, start, finish, remind_on, active, updated_by, created_by, created_date, updated_date ,card_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM reminder WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM reminder WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE reminder SET start=?, finish=?, remind_on=?, active=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public Reminder findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("Reminder with ID: " + id.toString() + " doesn't exists");
    }


    @Override
    public List<Reminder> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)){
            List<Reminder> result = new ArrayList<>();
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
    public Reminder create(Reminder entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getStart()));
            statement.setTimestamp(3, Timestamp.valueOf(entity.getEnd()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.getRemindOn()));
            statement.setBoolean(5, entity.getActive());
            statement.setString(6, entity.getUpdatedBy());
            statement.setString(7, entity.getCreatedBy());
            statement.setDate(8, entity.getCreatedDate());
            statement.setDate(9, entity.getUpdatedDate());
            statement.setObject(10, entity.getCardId());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return entity;
    }

    @Override
    public Reminder update(Reminder entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setTimestamp(1, Timestamp.valueOf(entity.getStart()));
            statement.setTimestamp(2, Timestamp.valueOf(entity.getEnd()));
            statement.setTimestamp(3, Timestamp.valueOf(entity.getRemindOn()));
            statement.setTimestamp(5, Timestamp.valueOf(updateDate));
            statement.setString(6, "Test");
            statement.setObject(7, UUID.randomUUID());
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
    private Reminder map(ResultSet rs) throws SQLException {
        Reminder reminder = new Reminder();
        reminder.setId(UUID.fromString(rs.getString("id")));
        reminder.setStart(LocalDateTime.parse(rs.getTimestamp("start").toLocalDateTime().toString()));
        reminder.setEnd(LocalDateTime.parse(rs.getTimestamp("end").toLocalDateTime().toString()));
        reminder.setRemindOn(LocalDateTime.parse(rs.getTimestamp("remind_on").toLocalDateTime().toString()));
        reminder.setActive(rs.getBoolean("active"));
        reminder.setUpdatedBy(rs.getString("updated_by"));
        reminder.setCreatedBy(rs.getString("created_by"));
        reminder.setCreatedDate(rs.getDate("created_date"));
        reminder.setUpdatedDate(rs.getDate("updated_date"));
        reminder.setCardId(UUID.fromString(rs.getString("card_id")));
        return reminder;
    }
}
