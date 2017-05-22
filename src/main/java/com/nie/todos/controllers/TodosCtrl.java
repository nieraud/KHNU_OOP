package com.nie.todos.controllers;

import com.google.gson.Gson;
import com.nie.todos.models.factory.DaoServiceFactory;
import com.nie.todos.models.repositories.TodosRepository;
import lombok.Getter;
import org.eclipse.jetty.http.HttpStatus;
import spark.Route;

/**
 * Created by inna on 20.05.17.
 */
public class TodosCtrl {
    @Getter
    private static final TodosRepository todosService = DaoServiceFactory.getTodosService();
    private static Gson gson = new Gson();


    @Getter
    private static final Route addProduct = (request, response) -> {
        return "";
    };

}
