package spd.trello.repository;

import spd.trello.domain.Comment;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentRepository implements CRUDRepository<Comment>{

    private final DataSource dataSource;

    public CommentRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO comment(id, text, updated_by, created_by, created_date, updated_date, card_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM comment WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM comment WHERE id=?";
    //need add  all fields from DB
    private static final String UPDATE_BY_STMT = "UPDATE comment SET  updated_by=?, updated_date=?, text=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM comment";

    @Override
    public Comment findById(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error CommentRepository findById" , e);
        }
        throw new IllegalStateException("Comment with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Comment> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)){
            List<Comment> result = new ArrayList<>();
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()){
                result.add(map(resultSet));
            }
            if(!result.isEmpty()){
                return result;
            }
        }catch (SQLException e){
            throw new IllegalStateException("Error CommentRepository getAll", e);
        }
        throw new IllegalStateException("Table Comment is empty");
    }

    @Override
    public Comment create(Comment entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getContent());
            statement.setString(3, entity.getUpdatedBy());
            statement.setString(4, entity.getCreatedBy());
            statement.setDate(5, entity.getCreatedDate());
            statement.setDate(6, entity.getUpdatedDate());
            statement.setObject(7, entity.getCardId());
            statement.setObject(8, entity.getUsersId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error CommentRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public Comment update(Comment entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, entity.getUpdatedBy());
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, entity.getContent());
            statement.setObject(4, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error CommentRepository update",e);
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
            throw new IllegalStateException("Error СommentRepository delete", e);
        }
    }
    private Comment map(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(UUID.fromString(rs.getString("id")));
        comment.setContent(rs.getString("text"));
        comment.setUpdatedBy(rs.getString("updated_by"));
        comment.setCreatedBy(rs.getString("created_by"));
        comment.setCreatedDate(rs.getDate("created_date"));
        comment.setUpdatedDate(rs.getDate("updated_date"));
        comment.setCardId(UUID.fromString(String.valueOf(rs.getString("card_id"))));
        comment.setUsersId(UUID.fromString(String.valueOf(rs.getString("user_id"))));
        return comment;
    }
}
