package spd.trello.repository;

import spd.trello.domain.Card;
import spd.trello.domain.CardList;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardListRepository implements CRUDRepository<CardList> {

    private static DataSource dataSource;


    private static final String CREATE_STMT = "INSERT INTO card_list(id, board_id, updated_by, created_by, created_date, updated_date, name, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM card_list WHERE id=?";
    private static final String FIND_ALL_FOR_BOARD_STMT = "SELECT * FROM card_list WHERE board_id=?;";
    private static final String DELETE_BY_STMT = "DELETE FROM card_list WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE card_list SET  updated_by=?, updated_date=?, name=?, archived=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";

    @Override
    public CardList findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("CardList with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<CardList> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STMT)) {
            List<CardList> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("CardListRepository::findAll failed", e);
        }
        throw new IllegalStateException("Table card_lists is empty!");
    }

    @Override
    public CardList create(CardList entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getCreatedBy());
            statement.setDate(2, entity.getCreatedDate());
            statement.setString(4, entity.getName());
            statement.setBoolean(5, entity.getArchived());
            statement.setObject(6, entity.getBoardId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CardList doesn't creates");
        }
        return entity;
    }

    @Override
    public CardList update(CardList entity) throws IllegalAccessException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_STMT)) {
            CardList oldCardList = findById(entity.getId());
            statement.setString(1, entity.getUpdatedBy());
            LocalDateTime updateDate = LocalDateTime.now();
            statement.setTimestamp(2, Timestamp.valueOf(updateDate));
            if (entity.getName() == null) {
                statement.setString(3, oldCardList.getName());
            } else {
                statement.setString(3, entity.getName());
            }
            statement.setBoolean(4, entity.getArchived());
            statement.setObject(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CardList with ID: " + entity.getId().toString() + " doesn't updates");
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

    public List<CardList> findAllForBoard(UUID boardId){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_FOR_BOARD_STMT)) {
            statement.setObject(1, boardId);
            List<CardList> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException("CardListRepository::findAllForBoard failed", e);
        }
    }
    private CardList map(ResultSet rs) throws SQLException {
        CardList cardList = new CardList();
        cardList.setId(UUID.fromString(rs.getString("id")));
        cardList.setBoardId(UUID.fromString(rs.getString("board_id")));
        cardList.setUpdatedBy(rs.getString("updated_by"));
        cardList.setCreatedBy(rs.getString("created_by"));
        cardList.setCreatedDate(rs.getDate("created_date"));
        cardList.setUpdatedDate(rs.getDate("updated_date"));
        cardList.setName(rs.getString("name"));
        cardList.setArchived(rs.getBoolean("archived"));
        return cardList;
    }
    private List<Card> getCardsForCardLists(UUID cardListId) {
        return new ArrayList<>();
    }
}
