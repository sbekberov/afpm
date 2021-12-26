package spd.trello.db;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;

public class Flyway_configuration {
    private static Flyway createFlyway(DataSource dataSource){
        return Flyway.configure().dataSource(dataSource).load();
    }
    public static void configure() throws IOException {
        Flyway flyway = createFlyway(DB_configuration.createDateSource());
        flyway.migrate();
    }
}
