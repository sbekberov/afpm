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

    private final DataSource dataSource;

    public CardListRepository(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO card_list(id, board_id, created_by, created_date,  name, archived) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM card_list WHERE id=?";
    private static final String FIND_ALL_FOR_BOARD_STMT = "SELECT * FROM card_list WHERE board_id=?;";
    private static final String DELETE_BY_STMT = "DELETE FROM card_list WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE card_list SET  updated_by=?, updated_date=?, name=?, archived=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM card_list";

    @Override
    public CardList findById(UUID id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_BY_STMT)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error CardListRepository findById" , e);
        }
        throw new IllegalStateException("CardList with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<CardList> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STMT)) {
            List<CardList> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error CardListRepository getAll", e);
        }
        throw new IllegalStateException("Table Card_list is empty!");
    }

    @Override
    public CardList create(CardList entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_STMT)) {
            statement.setObject(1, entity.getId());
            statement.setObject(2, entity.getBoardId());
            statement.setString(3, entity.getCreatedBy());
            statement.setDate(4, entity.getCreatedDate());
            statement.setString(5, entity.getName());
            statement.setBoolean(6, entity.getArchived());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error CardListRepository create",e);
        }
        return findById(entity.getId());
    }

    @Override
    public CardList update(CardList entity)  {
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
            throw new IllegalStateException("Error CardListRepository update");
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
            throw new IllegalStateException("Error CardListRepository delete" , e);
        }
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
