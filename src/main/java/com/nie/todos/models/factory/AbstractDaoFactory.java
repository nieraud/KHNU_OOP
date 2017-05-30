package com.nie.todos.models.factory;

import com.nie.todos.models.repositories.OAuthRepository;
import com.nie.todos.models.repositories.TodosRepository;
import com.nie.todos.models.services.OAuthDaoService;
import com.nie.todos.models.services.TodosDaoService;

/**
 * Created by inna on 20.05.17.
 */
public abstract class AbstractDaoFactory implements DataSource {
    AbstractDaoFactory() {
    }

    static class DAO_SERVICE {

        static final TodosRepository TODOS_REPOSITORY
                = new TodosDaoService(getInstance());

        static final OAuthRepository OAUTH_REPOSITORY
                = new OAuthDaoService(getInstance());


    }

    public static AbstractDaoFactory getInstance() {
        return PgDataSource.getInstance();
    }

}
