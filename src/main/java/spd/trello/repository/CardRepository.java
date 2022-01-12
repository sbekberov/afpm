package spd.trello.repository;

import spd.trello.domain.Card;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardRepository implements CRUDRepository<Card>{

    private static DataSource dataSource;



    private static final String CREATE_STMT = "INSERT INTO card(id, cardlist_id, updated_by, created_by, created_date, updated_date, name, archived, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM card WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM card WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE card SET  updated_by=?, updated_date=?, name=?, archived=?, description=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public Card findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("Card with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Card> getAll() {
        try( Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_STMT);
           List<Card> result = new ArrayList<>();
            while (rs.next()) {
                result.add(map(rs));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Table card  is empty!");
    }

    @Override
    public Card create(Card entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setObject(2, entity.getCardListId());
            statement.setString(3, entity.getUpdatedBy());
            statement.setString(4, entity.getCreatedBy());
            statement.setDate(5, entity.getCreatedDate());
            statement.setDate(6, entity.getUpdatedDate());
            statement.setString(7, entity.getName());
            statement.setBoolean(8, entity.getArchived());
            statement.setString(9, entity.getDescription());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    @Override
    public Card update(Card entity) {
        LocalDateTime updateDate = LocalDateTime.now();
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_BY_STMT)){
            statement.setString(1, "Test");
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            statement.setString(3, "Test");
            statement.setBoolean(4, Boolean.TRUE);
            statement.setString(5, "Test description");
            statement.setObject(6, UUID.fromString("6fa408e2-e42d-4ad3-9233-87691323acec"));

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
    private Card map(ResultSet rs) throws SQLException {
        Card card = new Card();
        card.setId(UUID.fromString(rs.getString("id")));
        card.setCardListId(UUID.fromString(rs.getString("cardlist_id")));
        card.setUpdatedBy(rs.getString("updated_by"));
        card.setCreatedBy(rs.getString("created_by"));
        card.setCreatedDate((rs.getDate("created_date")));
        card.setUpdatedDate((rs.getDate("updated_date")));
        card.setName(rs.getString("name"));
        card.setArchived(rs.getBoolean("archived"));
        card.setDescription(rs.getString("description"));
        return card;
    }
}
