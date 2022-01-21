package spd.trello.repository;

import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardRepository implements CRUDRepository<Board>{


    private final DataSource dataSource;

    public BoardRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO board(id, workspace_id, updated_by, created_by, created_date, updated_date, name, archived, visibility, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM board WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM board WHERE id=?";
    private static final String UPDATE_BY_STMT ="UPDATE board SET updated_by=? ,updated_date=?, name=?, description=?, visibility=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";


    @Override
    public Board findById(UUID id) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)){
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error BoardRepository findById" , e);
        }
        throw new IllegalStateException("Board with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public Board create(Board entity) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(CREATE_STMT)){
            statement.setObject(1, entity.getId());
            statement.setObject(2, entity.getWorkspaceId());
            statement.setString(3, entity.getUpdatedBy());
            statement.setString(4, entity.getCreatedBy());
            statement.setDate(5, entity.getCreatedDate());
            statement.setDate(6,entity.getUpdatedDate());
            statement.setString(7, entity.getName());
            statement.setBoolean(8, entity.getArchived());
            statement.setString(9, String.valueOf(entity.getVisibility()));
            statement.setString(10, entity.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error BoardRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public Board update(Board entity)  {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, entity.getUpdatedBy());
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, entity.getName());
            statement.setString(4,entity.getDescription());
            statement.setString(5, String.valueOf(entity.getVisibility()));
            statement.setBoolean(6,entity.getArchived());
            statement.setObject(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error BoardRepository update");
        }
        return findById(entity.getId());
    }


    @Override
    public boolean delete(UUID id) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(DELETE_BY_STMT)){
            statement.setObject(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new IllegalStateException("Error BoardRepository delete", e);
        }
    }

    @Override
    public List<Board> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)) {
            List<Board> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error BoardRepository getAll", e);
        }
        throw new IllegalStateException("Table board is empty!");
    }

    private Board map(ResultSet rs) throws SQLException {
        Board board = new Board();
        board.setId(UUID.fromString(rs.getString("id")));
        board.setId(UUID.fromString(rs.getString("workspace_id")));
        board.setCreatedDate((rs.getDate("created_date")));
        board.setUpdatedDate((rs.getDate("updated_date")));
        board.setName(rs.getString("name"));
        board.setUpdatedBy(rs.getString("updated_by"));
        board.setCreatedBy(rs.getString("created_by"));
        board.setArchived(rs.getBoolean("archived"));
        board.setVisibility(BoardVisibility.valueOf(rs.getString("visibility")));
        board.setDescription(rs.getString("description"));
        return board;
    }
}
