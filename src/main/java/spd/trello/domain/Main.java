package spd.trello.domain;

import spd.trello.db.FlywayConfiguration;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FlywayConfiguration.configure();
    }

}




