package spd.trello.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spd.trello.db.DbConfiguration;
import spd.trello.db.FlywayConfiguration;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = {"spd.trello.repository", "spd.trello.service"})
public class Config implements InitializingBean {

    @Bean
    public DataSource dataSource(){
        return DbConfiguration.createDateSource();
    }

    @Override
    public void afterPropertiesSet(){
        FlywayConfiguration.configure();
    }

}

