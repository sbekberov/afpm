package spd.trello.domain;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spd.trello.configuration.Config;
import spd.trello.db.FlywayConfiguration;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FlywayConfiguration.configure();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] arr = context.getBeanDefinitionNames();
        System.out.println(arr);
    }
}




