package com.nie.todos.models.services;

import com.nie.todos.models.factory.AbstractDaoFactory;

/**
 * Created by inna on 20.05.17.
 */
public class AbstractDaoService {
    protected AbstractDaoFactory daoFactory;

    public AbstractDaoService(AbstractDaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
}
