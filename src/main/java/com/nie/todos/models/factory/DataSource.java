package com.nie.todos.models.factory;

import org.sql2o.Sql2o;

/**
 * Created by inna on 20.05.17.
 */
public interface DataSource {
    Sql2o getDataSource();
}
