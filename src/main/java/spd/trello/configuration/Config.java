package spd.trello.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spd.trello.db.DbConfiguration;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
    @ComponentScan(basePackages = {"spd.trello.repository", "spd.trello.service"})
    public class Config {

        @Bean
        public DataSource dataSource() throws IOException {
            return DbConfiguration.createDateSource();
        }

    }

