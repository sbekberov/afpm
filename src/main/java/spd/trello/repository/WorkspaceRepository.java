package spd.trello.repository;

import spd.trello.db.DbConfiguration;
import spd.trello.domain.Workspace;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.UUID;

public class WorkspaceRepository {
    public void add(Workspace workspace) throws SQLException, IOException {
        UUID id = workspace.getId();
        String createdBy = workspace.getCreatedBy();
        LocalDateTime createdDate = workspace.getCreatedDate();
        String name = workspace.getName();
        String description = workspace.getDescription();
        String updatedBy = null;
        if (workspace.getUpdatedBy() != null) {
            updatedBy = workspace.getUpdatedBy();
        }
        LocalDateTime updatedDate = null;
        if (workspace.getUpdatedDate() != null) {
            updatedDate = workspace.getUpdatedDate();
        }

        try (Connection connection = DbConfiguration.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(

                  "insert into workspace(id,updated_by,created_by,created_date,updated_date,name,description)" +
                            "VALUES('" + id + "', '" + (updatedBy == null ? " null);" : " '" + updatedBy + "');") + "', '" +
                            createdBy + "','" + createdDate + "', '" + name + "', '"
                            + description + (updatedBy == null ? " null);" : " '" + updatedBy;
            );


        }


    }
}
