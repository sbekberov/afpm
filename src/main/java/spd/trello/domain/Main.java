package spd.trello.domain;

import spd.trello.db.Flyway_configuration;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Flyway_configuration.configure();
    }


}

