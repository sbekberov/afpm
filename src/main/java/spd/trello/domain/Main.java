package spd.trello.domain;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = createDateSource();

        Connection connection = dataSource.getConnection();
        connection.close();
    }

    private static DataSource createDateSource() throws IOException {
        Properties properties = loadProperties();
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));

        int maxConnection = Integer.parseInt(properties.getProperty("jdbc.pool.max"));
        cfg.setMaximumPoolSize(maxConnection);

        return new HikariDataSource(cfg);
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(in);

        return properties;
    }
}

