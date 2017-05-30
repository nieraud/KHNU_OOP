package com.nie.todos.models.factory;

import com.nie.todos.models.repositories.OAuthRepository;
import com.nie.todos.models.repositories.TodosRepository;
import org.sql2o.Sql2o;

/**
 * Created by inna on 20.05.17.
 */
public class DaoServiceFactory extends AbstractDaoFactory{
    public Sql2o getDataSource() {
        return AbstractDaoFactory.getInstance().getDataSource();
    }

    public static TodosRepository getTodosService() {
        return DAO_SERVICE.TODOS_REPOSITORY;
    }

    public static OAuthRepository getOAuthService() {
        return DAO_SERVICE.OAUTH_REPOSITORY;
    }



}