package spd.trello.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import spd.trello.domain.Main;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfiguration {

    private static DataSource dataSource;

    public static DataSource getDataSource()  {
        if (dataSource==null) {
            try {
                dataSource=createDateSource();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

    public static DataSource createDateSource() throws IOException {
        Properties properties = loadProperties();
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("db.url"));
        cfg.setUsername(properties.getProperty("db.username"));
        cfg.setPassword(properties.getProperty("db.password"));

        int maxConnection = Integer.parseInt(properties.getProperty("db.pool.max"));
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
