package spd.trello.repository;

import spd.trello.domain.Attachment;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttachmentRepository implements CRUDRepository<Attachment>{

    private final DataSource dataSource;

    public AttachmentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO attachment(id, link, name, updated_by, created_by, created_date, updated_date, comment_id, card_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM attachment WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM attachment WHERE id=?";
    private static final String UPDATE_BY_STMT ="UPDATE attachment SET  link=?, name=?,updated_by=?, updated_date=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public Attachment findById(UUID id) throws IllegalAccessException {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)){
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalAccessException("attachment with ID: " + id.toString() + " doesn't exists");
    }


    @Override
    public List<Attachment> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)){
            List<Attachment> result = new ArrayList<>();
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
    public Attachment create(Attachment entity) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(CREATE_STMT)){
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getLink());
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getUpdatedBy());
            statement.setString(5, entity.getCreatedBy());
            statement.setDate(6,   entity.getCreatedDate());
            statement.setObject(7, entity.getCommentId());
            statement.setObject(8, entity.getCardId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }


    @Override
    public Attachment update(Attachment entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){

            statement.setString(1, "Test");
            statement.setString(2, "Test");
            statement.setString(3, "Test");
            statement.setTimestamp(4, Timestamp.valueOf(updateDate));
            statement.setObject(5, UUID.fromString("6fa408e2-e42d-4ad3-9233-87691323acec"));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean delete(UUID id) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(DELETE_BY_STMT)){
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    private Attachment map(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(UUID.fromString(rs.getString("id")));
        attachment.setName(rs.getString("name"));
        attachment.setLink(rs.getString("link"));
        attachment.setCreatedDate((rs.getDate("created_date")));
        attachment.setUpdatedDate((rs.getDate("updated_date")));
        attachment.setUpdatedBy(rs.getString("updated_by"));
        attachment.setCreatedBy(rs.getString("created_by"));
        attachment.setCommentId(UUID.fromString(rs.getString("comment_id")));
        attachment.setCardId(UUID.fromString(rs.getString("card_id")));
        return attachment;
    }

}
