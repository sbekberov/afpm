package spd.trello.repository;

import spd.trello.domain.Reminder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReminderRepository implements CRUDRepository<Reminder>{

    private final DataSource dataSource;
    public ReminderRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }


    private static final String CREATE_STMT = "INSERT INTO reminder(id, start, finish, remind_on, active, updated_by, created_by, created_date, updated_date ,card_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM reminder WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM reminder WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE reminder SET start=?, finish=?, remind_on=?, active=?,updated_date=?,updated_by=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM reminder";

    @Override
    public Reminder findById(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error ReminderRepository findByID");
        }
        throw new IllegalStateException("Reminder with ID: " + id.toString() + " doesn't exists");
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
            throw new IllegalStateException("Error ReminderRepository getAll", e);
        }
        throw new IllegalStateException("Table Reminder is empty");
    }

    @Override
    public Reminder create(Reminder entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setDate(2, entity.getStart());
            statement.setDate(3, entity.getEnd());
            statement.setDate(4, entity.getRemindOn());
            statement.setBoolean(5, entity.getActive());
            statement.setString(6, entity.getUpdatedBy());
            statement.setString(7, entity.getCreatedBy());
            statement.setDate(8, entity.getCreatedDate());
            statement.setDate(9, entity.getUpdatedDate());
            statement.setObject(10, entity.getCardId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error ReminderRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public Reminder update(Reminder entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setDate(1, entity.getStart());
            statement.setDate(2, entity.getEnd());
            statement.setDate(3, entity.getRemindOn());
            statement.setBoolean(4, entity.getActive());
            statement.setDate(5,entity.getUpdatedDate());
            statement.setString(6,entity.getUpdatedBy());
            statement.setObject(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error ReminderRepository update", e);
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
            throw new IllegalStateException("Error ReminderRepository delete", e);
        }
    }
    private Reminder map(ResultSet rs) throws SQLException {
        Reminder reminder = new Reminder();
        reminder.setId(UUID.fromString(rs.getString("id")));
        reminder.setStart(rs.getDate("start"));
        reminder.setEnd(rs.getDate("finish"));
        reminder.setRemindOn(rs.getDate("remind_on"));
        reminder.setActive(rs.getBoolean("active"));
        reminder.setUpdatedBy(rs.getString("updated_by"));
        reminder.setCreatedBy(rs.getString("created_by"));
        reminder.setCreatedDate(rs.getDate("created_date"));
        reminder.setUpdatedDate(rs.getDate("updated_date"));
        reminder.setCardId(UUID.fromString(rs.getString("card_id")));
        return reminder;
    }
}
