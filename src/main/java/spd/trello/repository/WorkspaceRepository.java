package spd.trello.repository;


import spd.trello.domain.Workspace;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkspaceRepository implements CRUDRepository<Workspace> {

    private final DataSource dataSource;

    public WorkspaceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String CREATE_STMT = "INSERT INTO workspace(id, updated_by, created_by, created_date, updated_date, name, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_STMT = "SELECT * FROM workspace WHERE id=?";
    private static final String DELETE_BY_STMT = "DELETE FROM workspace WHERE id=?";
    private static final String UPDATE_BY_STMT = "UPDATE workspace SET updated_by=? ,updated_date=?, name=?, description=? WHERE id=?";
    private static final String GET_ALL_STMT = "SELECT * FROM workspace";


    @Override
    public List<Workspace> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STMT)) {
            List<Workspace> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            if (!result.isEmpty()) {
                return result;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("WorkspaceRepository::findAll failed", e);
        }
        throw new IllegalStateException("Table workspaces is empty!");
    }

    @Override
    public Workspace findById(UUID id) throws IllegalAccessException {
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
        throw new IllegalAccessException("workspace with ID: " + id.toString() + " doesn't exists");
    }


    @Override
    public Workspace create (Workspace entity) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(CREATE_STMT)){
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getCreatedBy());
            statement.setDate(3, entity.getCreatedDate());
            statement.setString(4, entity.getName());
            statement.setString(5, entity.getDescription());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    @Override
    public Workspace update(Workspace entity) throws IllegalAccessException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_STMT)) {
            Workspace oldWorkspace = findById(entity.getId());
            statement.setString(1, entity.getUpdatedBy());
            statement.setDate(2, entity.getUpdatedDate());
            if (entity.getName() == null) {
                statement.setString(3, oldWorkspace.getName());
            } else {
                statement.setString(3, entity.getName());
            }
            if (entity.getDescription() == null) {
                statement.setString(4, oldWorkspace.getDescription());
            } else {
                statement.setString(4, entity.getDescription());
            }
            if (entity.getVisibility() == null) {
                statement.setString(5, oldWorkspace.getVisibility().toString());
            } else {
                statement.setString(5, entity.getVisibility().toString());
            }
            statement.setObject(6, entity.getId());
            statement.executeUpdate();


        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalStateException("Workspace with ID: " + entity.getId().toString() + " doesn't updates");
        }
        return findById(entity.getId());
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

    public Workspace map(ResultSet rs) throws SQLException {
        Workspace workspace = new Workspace();
        workspace.setId(UUID.fromString(rs.getString("id")));
        workspace.setCreatedDate(rs.getDate("created_date"));
        workspace.setUpdatedDate(rs.getDate("updated_date"));
        workspace.setName(rs.getString("name"));
        workspace.setUpdatedBy(rs.getString("updated_by"));
        workspace.setCreatedBy(rs.getString("created_by"));
        workspace.setDescription(rs.getString("description"));
        return workspace;
    }

}
