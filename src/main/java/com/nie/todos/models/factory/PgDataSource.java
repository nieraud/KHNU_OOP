package com.nie.todos.models.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

/**
 * Created by inna on 20.05.17.
 */
public class PgDataSource extends AbstractDaoFactory implements DataSource {
    private volatile static PgDataSource dataSource;
    private Sql2o sql2o;

    private PgDataSource() {

    }

    public static PgDataSource getInstance() {
        if (dataSource == null) {
            synchronized (PgDataSource.class) {
                if (dataSource == null) {
                    dataSource = new PgDataSource();
                }
            }
        }
        return dataSource;
    }

    public Sql2o getDataSource() {
        if (sql2o == null) {
            synchronized ((PgDataSource.class)) {
                if (sql2o == null) {
                    sql2o = new Sql2o(getDataSourcePg());
                }
            }
        }
        return sql2o;
    }

    private HikariDataSource getDataSourcePg() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/todos");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1565");

        return new HikariDataSource(hikariConfig);
    }
}