package spd.trello.service;

import spd.trello.db.DbConfiguration;
import spd.trello.domain.Domain;

import javax.sql.DataSource;

public abstract class AbstractService <T extends Domain> {
    protected DataSource dataSource;
    public AbstractService() {
        this.dataSource = DbConfiguration.getDataSource();
    }
}
